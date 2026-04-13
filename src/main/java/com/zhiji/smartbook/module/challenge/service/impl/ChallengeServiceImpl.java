package com.zhiji.smartbook.module.challenge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhiji.smartbook.module.challenge.entity.Challenge;
import com.zhiji.smartbook.module.challenge.mapper.ChallengeMapper;
import com.zhiji.smartbook.module.challenge.service.ChallengeService;
import com.zhiji.smartbook.common.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeMapper challengeMapper;

    public Challenge getCurrentChallenge() {
        return challengeMapper.selectOne(new LambdaQueryWrapper<Challenge>()
                .eq(Challenge::getUserId, UserContext.get())
                .eq(Challenge::getStatus, 0)
                .orderByDesc(Challenge::getCreatedAt)
                .last("LIMIT 1"));
    }
}