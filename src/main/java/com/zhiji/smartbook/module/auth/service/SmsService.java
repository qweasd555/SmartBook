package com.zhiji.smartbook.module.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhiji.smartbook.common.utils.RedisUtils;
import com.zhiji.smartbook.config.SmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private static final String SMS_CODE_KEY = "sms:code:";
    private static final String SMS_LOCK_KEY = "sms:lock:";
    private static final int CODE_TTL_SECONDS = 300; // 验证码有效期5分钟
    private static final int SEND_INTERVAL_SECONDS = 60; // 发送间隔60秒

    private final SmsProperties smsProperties;
    private final RedisUtils redisUtils;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 发送短信验证码
     * @return 验证码（仅开发环境日志输出，生产不返回）
     */
    public void sendCode(String mobile) {
        // 60秒内不允许重复发送
        String lockKey = SMS_LOCK_KEY + mobile;
        if (Boolean.TRUE.equals(redisUtils.hasKey(lockKey))) {
            long remain = redisUtils.getExpire(lockKey, TimeUnit.SECONDS);
            throw new RuntimeException("发送过于频繁，请 " + remain + " 秒后重试");
        }

        String code = generateCode();

        if (smsProperties.isEnabled()) {
            doSend(mobile, code);
        } else {
            // 未启用时仅打印，方便本地开发
            log.info("[SMS MOCK] mobile={} code={}", mobile, code);
        }

        // 存入 Redis，验证码5分钟有效
        redisUtils.set(SMS_CODE_KEY + mobile, code, CODE_TTL_SECONDS, TimeUnit.SECONDS);
        // 60秒发送锁
        redisUtils.set(lockKey, "1", SEND_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 校验验证码，验证成功后立即删除
     */
    public boolean verifyCode(String mobile, String code) {
        String key = SMS_CODE_KEY + mobile;
        String stored = redisUtils.get(key);
        if (stored != null && stored.equals(code)) {
            redisUtils.delete(key);
            return true;
        }
        return false;
    }

    private void doSend(String mobile, String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", smsProperties.getApiKey());
        params.add("mobile", mobile);
        params.add("tpl_id", String.valueOf(smsProperties.getTplId()));
        params.add("tpl_value", "#code#=" + code);
        if (smsProperties.getExt() != null && !smsProperties.getExt().isBlank()) {
            params.add("ext", smsProperties.getExt());
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        String response = restTemplate.postForObject(smsProperties.getUrl(), request, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            int errorCode = root.path("error_code").asInt(-1);
            if (errorCode != 0) {
                String reason = root.path("reason").asText();
                if (reason == null || reason.isBlank()) reason = "未知错误";
                log.error("[SMS] 发送失败 mobile={} error_code={} reason={}", mobile, errorCode, reason);
                throw new RuntimeException("短信发送失败：" + reason);
            }
            log.info("[SMS] 发送成功 mobile={}", mobile);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("[SMS] 解析响应失败 response={}", response, e);
            throw new RuntimeException("短信服务异常，请稍后重试");
        }
    }

    private String generateCode() {
        return String.format("%06d", new SecureRandom().nextInt(1000000));
    }
}
