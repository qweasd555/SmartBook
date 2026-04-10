package com.zhiji.smartbook.module.user.service;

import com.zhiji.smartbook.module.user.dto.UserProfileUpdateDTO;
import com.zhiji.smartbook.module.user.vo.UserDashboardVO;
import com.zhiji.smartbook.module.user.vo.UserVO;

public interface UserService {

    UserVO getCurrentUser();

    UserVO updateCurrentUser(UserProfileUpdateDTO request);

    UserDashboardVO getDashboard(String range);
}
