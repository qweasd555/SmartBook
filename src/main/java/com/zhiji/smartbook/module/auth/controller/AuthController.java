package com.zhiji.smartbook.module.auth.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.auth.dto.AuthLoginDTO;
import com.zhiji.smartbook.module.auth.dto.SendSmsCodeDTO;
import com.zhiji.smartbook.module.auth.service.AuthService;
import com.zhiji.smartbook.module.auth.vo.LoginVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sms-code")
    public Result<Void> sendSmsCode(@Valid @RequestBody SendSmsCodeDTO request) {
        authService.sendSmsCode(request.getMobile());
        return Result.success();
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody AuthLoginDTO request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }
}
