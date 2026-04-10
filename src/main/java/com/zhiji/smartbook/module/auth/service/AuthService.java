package com.zhiji.smartbook.module.auth.service;

import com.zhiji.smartbook.module.auth.dto.AuthLoginDTO;
import com.zhiji.smartbook.module.auth.dto.RefreshTokenDTO;
import com.zhiji.smartbook.module.auth.vo.LoginVO;

public interface AuthService {

    LoginVO login(AuthLoginDTO request);

    LoginVO refreshToken(RefreshTokenDTO request);

    void logout();
}
