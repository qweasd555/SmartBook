package com.zhiji.smartbook.module.auth.dto;

import lombok.Data;

@Data
public class AuthLoginDTO {
    private String loginType;
    private String code;
    private String mobile;
    private String password;
    private String nickname;
    private String avatarUrl;
}
