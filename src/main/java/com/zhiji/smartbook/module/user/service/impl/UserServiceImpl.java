package com.zhiji.smartbook.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiji.smartbook.common.utils.UserContext;
import com.zhiji.smartbook.module.user.dto.UserProfileUpdateDTO;
import com.zhiji.smartbook.module.user.entity.User;
import com.zhiji.smartbook.module.user.mapper.UserMapper;
import com.zhiji.smartbook.module.user.service.UserService;
import com.zhiji.smartbook.module.user.vo.UserDashboardVO;
import com.zhiji.smartbook.module.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserVO getCurrentUser() {
        User user = userMapper.selectById(UserContext.get());
        return toVO(user);
    }

    @Override
    public UserVO updateCurrentUser(UserProfileUpdateDTO request) {
        User user = userMapper.selectById(UserContext.get());
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
        userMapper.updateById(user);
        return toVO(user);
    }

    @Override
    public UserDashboardVO getDashboard(String range) {
        // TODO: 聚合账单数据，暂时返回空结构
        UserDashboardVO dashboard = new UserDashboardVO();
        dashboard.setRange(range);
        dashboard.setNetBalance(0.0);
        dashboard.setIncomeAmount(0.0);
        dashboard.setExpenseAmount(0.0);
        dashboard.setTodayExpense(0.0);
        dashboard.setTodayIncome(0.0);
        dashboard.setBudgetRemain(0.0);
        return dashboard;
    }

    private UserVO toVO(User user) {
        if (user == null) return null;
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setNickname(user.getNickname());
        vo.setAvatarUrl(user.getAvatarUrl());
        vo.setMobile(user.getMobile());
        vo.setStatus(user.getStatus() == 0 ? "NORMAL" : "DISABLED");
        return vo;
    }
}
