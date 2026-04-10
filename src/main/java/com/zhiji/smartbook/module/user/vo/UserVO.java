package com.zhiji.smartbook.module.user.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long id;
    private String nickname;
    private String avatarUrl;
    private String mobile;
    private String userType;
    private Integer financialScore;
    private String status;
}
