package com.zhiji.smartbook.module.auth.service.impl;

import com.zhiji.smartbook.module.auth.dto.AuthLoginDTO;
import com.zhiji.smartbook.module.auth.dto.RefreshTokenDTO;
import com.zhiji.smartbook.module.auth.service.AuthService;
import com.zhiji.smartbook.module.auth.vo.LoginVO;
import com.zhiji.smartbook.module.user.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginVO login(AuthLoginDTO request) {
        UserVO user = new UserVO();
        user.setId(10001L);
        user.setNickname(request.getNickname() == null || request.getNickname().isBlank() ? "小智" : request.getNickname());
        user.setAvatarUrl(request.getAvatarUrl() == null || request.getAvatarUrl().isBlank()
                ? "https://xx.com/avatar.png" : request.getAvatarUrl());
        user.setUserType("WORKER");
        user.setFinancialScore(85);
        user.setMobile("13800000000");
        user.setStatus("NORMAL");

        LoginVO loginVO = new LoginVO();
        loginVO.setToken("mock-jwt-token");
        loginVO.setRefreshToken("mock-refresh-token");
        loginVO.setExpiresIn(7200);
        loginVO.setUser(user);
        return loginVO;
    }

    @Override
    public LoginVO refreshToken(RefreshTokenDTO request) {
        AuthLoginDTO loginDTO = new AuthLoginDTO();
        loginDTO.setNickname("小智");
        return login(loginDTO);
    }

    @Override
    public void logout() {
        // 首期联调阶段仅返回成功
    }
}
