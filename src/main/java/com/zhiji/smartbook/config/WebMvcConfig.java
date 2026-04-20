package com.zhiji.smartbook.config;

import com.zhiji.smartbook.common.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

//    private final JwtInterceptor jwtInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/api/**")
//                .excludePathPatterns(
//                        "/api/auth/sms-code",
//                        "/api/auth/login",
//                        // 👇 下面是你要加的（开发模式，全部放行测试）
//                        "/api/reports/**",
//                        "/api/transactions/**"
//                );
//    }
}
