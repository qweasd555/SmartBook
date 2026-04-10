package com.zhiji.smartbook.module.user.controller;

import com.zhiji.smartbook.common.response.Result;
import com.zhiji.smartbook.module.user.dto.UserProfileUpdateDTO;
import com.zhiji.smartbook.module.user.service.UserService;
import com.zhiji.smartbook.module.user.vo.UserDashboardVO;
import com.zhiji.smartbook.module.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Result<UserVO> getCurrentUser() {
        return Result.success(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public Result<UserVO> updateCurrentUser(@RequestBody UserProfileUpdateDTO request) {
        return Result.success(userService.updateCurrentUser(request));
    }

    @GetMapping("/me/dashboard")
    public Result<UserDashboardVO> getDashboard(@RequestParam(defaultValue = "TODAY") String range) {
        return Result.success(userService.getDashboard(range));
    }
}
