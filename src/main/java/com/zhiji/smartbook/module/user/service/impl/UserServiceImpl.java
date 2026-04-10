package com.zhiji.smartbook.module.user.service.impl;

import com.zhiji.smartbook.module.user.dto.UserProfileUpdateDTO;
import com.zhiji.smartbook.module.user.service.UserService;
import com.zhiji.smartbook.module.user.vo.UserDashboardVO;
import com.zhiji.smartbook.module.user.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserVO getCurrentUser() {
        UserVO user = new UserVO();
        user.setId(10001L);
        user.setNickname("小智");
        user.setAvatarUrl("https://xx.com/avatar.png");
        user.setMobile("13800000000");
        user.setUserType("WORKER");
        user.setFinancialScore(85);
        user.setStatus("NORMAL");
        return user;
    }

    @Override
    public UserVO updateCurrentUser(UserProfileUpdateDTO request) {
        UserVO user = getCurrentUser();
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getUserType() != null) {
            user.setUserType(request.getUserType());
        }
        return user;
    }

    @Override
    public UserDashboardVO getDashboard(String range) {
        UserDashboardVO dashboard = new UserDashboardVO();
        dashboard.setRange(range);
        dashboard.setNetBalance(12480.50);
        dashboard.setIncomeAmount(15000.00);
        dashboard.setExpenseAmount(2519.50);
        dashboard.setTodayExpense(56.30);
        dashboard.setTodayIncome(0.00);
        dashboard.setBudgetRemain(0.00);
        return dashboard;
    }
}
