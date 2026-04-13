package com.zhiji.smartbook.module.auth.service;

import com.zhiji.smartbook.module.auth.dto.AuthLoginDTO;
import com.zhiji.smartbook.module.auth.vo.LoginVO;

public interface AuthService {

    void sendSmsCode(String mobile);

    LoginVO login(AuthLoginDTO request);

    void logout();
}
