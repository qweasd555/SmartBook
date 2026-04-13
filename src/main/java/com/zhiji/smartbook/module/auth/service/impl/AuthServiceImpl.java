package com.zhiji.smartbook.module.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiji.smartbook.common.utils.JwtUtils;
import com.zhiji.smartbook.common.utils.RedisUtils;
import com.zhiji.smartbook.module.auth.dto.AuthLoginDTO;
import com.zhiji.smartbook.module.auth.service.AuthService;
import com.zhiji.smartbook.module.auth.service.SmsService;
import com.zhiji.smartbook.module.auth.vo.LoginVO;
import com.zhiji.smartbook.module.user.entity.User;
import com.zhiji.smartbook.module.user.mapper.UserMapper;
import com.zhiji.smartbook.module.user.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_BLACKLIST_KEY = "token:blacklist:";

    private final UserMapper userMapper;
    private final SmsService smsService;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final HttpServletRequest httpServletRequest;

    @Value("${security.jwt.ttlSeconds}")
    private int ttlSeconds;

    @Override
    public void sendSmsCode(String mobile) {
        smsService.sendCode(mobile);
    }

    @Override
    public LoginVO login(AuthLoginDTO request) {
        if (!smsService.verifyCode(request.getMobile(), request.getCode())) {
            throw new RuntimeException("验证码错误或已过期");
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getMobile, request.getMobile()));
        if (user == null) {
            user = new User();
            user.setMobile(request.getMobile());
            user.setNickname("用户" + request.getMobile().substring(7));
            user.setStatus(0);
            userMapper.insert(user);
        }

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(jwtUtils.generateToken(user.getId()));
        loginVO.setExpiresIn(ttlSeconds);
        loginVO.setUser(toVO(user));
        return loginVO;
    }

    @Override
    public void logout() {
        String header = httpServletRequest.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            // 存入黑名单，TTL 与 token 有效期一致，到期自动清除
            redisUtils.set(TOKEN_BLACKLIST_KEY + token, "1", ttlSeconds, TimeUnit.SECONDS);
        }
    }

    private UserVO toVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setMobile(user.getMobile());
        vo.setStatus(user.getStatus() == 0 ? "NORMAL" : "DISABLED");
        return vo;
    }
}
