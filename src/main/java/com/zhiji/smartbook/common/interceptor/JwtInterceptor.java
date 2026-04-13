package com.zhiji.smartbook.common.interceptor;

import com.zhiji.smartbook.common.utils.JwtUtils;
import com.zhiji.smartbook.common.utils.RedisUtils;
import com.zhiji.smartbook.common.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private static final String TOKEN_BLACKLIST_KEY = "token:blacklist:";

    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":40101,\"message\":\"未登录或token失效\",\"data\":null}");
            return false;
        }

        String token = header.substring(7);

        // 检查黑名单
        if (Boolean.TRUE.equals(redisUtils.hasKey(TOKEN_BLACKLIST_KEY + token))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":40101,\"message\":\"未登录或token失效\",\"data\":null}");
            return false;
        }

        try {
            Long userId = jwtUtils.parseUserId(token);
            UserContext.set(userId);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":40101,\"message\":\"未登录或token失效\",\"data\":null}");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
