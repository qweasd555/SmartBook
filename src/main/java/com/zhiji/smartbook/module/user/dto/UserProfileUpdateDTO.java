package com.zhiji.smartbook.module.user.dto;

import lombok.Data;

@Data
public class UserProfileUpdateDTO {
    private String nickname;
    private String avatarUrl;
    private String userType;
}
