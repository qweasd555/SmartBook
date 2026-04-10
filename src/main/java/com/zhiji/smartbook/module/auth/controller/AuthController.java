package com.zhiji.smartbook.module.auth.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.auth.dto.AuthLoginDTO;
import com.zhiji.smartbook.module.auth.dto.RefreshTokenDTO;
import com.zhiji.smartbook.module.auth.service.AuthService;
import com.zhiji.smartbook.module.auth.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody AuthLoginDTO request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/refresh")
    public Result<LoginVO> refresh(@RequestBody RefreshTokenDTO request) {
        return Result.success(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }
}
