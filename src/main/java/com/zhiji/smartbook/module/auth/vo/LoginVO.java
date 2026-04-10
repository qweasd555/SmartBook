package com.zhiji.smartbook.module.auth.vo;

import com.zhiji.smartbook.module.user.vo.UserVO;
import lombok.Data;

@Data
public class LoginVO {
    private String token;
    private String refreshToken;
    private Integer expiresIn;
    private UserVO user;
}
