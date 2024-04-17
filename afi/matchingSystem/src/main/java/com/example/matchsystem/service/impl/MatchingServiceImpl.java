package com.example.matchsystem.service.impl;

import com.example.matchsystem.service.MatchingService;
import com.example.matchsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/24
 */
@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool m = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        m.addPlayer(userId, rating, botId);
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        m.removePlayer(userId);
        return "remove player success";
    }
}
