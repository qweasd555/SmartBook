// 路径：com.zhiji.smartbook.module.user.service.impl.UserServiceImpl.java
package com.zhiji.smartbook.module.user.service.impl;

import com.zhiji.smartbook.module.user.dto.UserProfileUpdateDTO;
import com.zhiji.smartbook.module.user.entity.User;
import com.zhiji.smartbook.module.user.mapper.UserMapper;
import com.zhiji.smartbook.module.user.service.UserService;
import com.zhiji.smartbook.module.user.vo.UserDashboardVO;
import com.zhiji.smartbook.module.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service; // ✅ 必须有这个注解！
import java.math.BigDecimal;

@Service // ✅ 必须有这个注解！
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserVO getCurrentUser() {
        User user = userMapper.selectById(1L); // 临时用1L，等登录模块做好了改回UserContext
        return toVO(user);
    }

    @Override
    public UserVO updateCurrentUser(UserProfileUpdateDTO request) {
        User user = userMapper.selectById(1L);
        if (request.getNickname() != null) user.setNickname(request.getNickname());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
        userMapper.updateById(user);
        return toVO(user);
    }

    @Override
    public UserDashboardVO getDashboard(String range) {
        UserDashboardVO vo = new UserDashboardVO();
        vo.setNetBalance(BigDecimal.ZERO);
        vo.setIncomeTotal(BigDecimal.ZERO);
        vo.setExpenseTotal(BigDecimal.ZERO);

        UserDashboardVO.ChallengeVO challenge = new UserDashboardVO.ChallengeVO();
        challenge.setCompletedDays(0);
        challenge.setTotalDays(21);
        vo.setChallenge(challenge);

        return vo;
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