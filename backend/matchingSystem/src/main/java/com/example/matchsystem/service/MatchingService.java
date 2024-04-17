package com.example.matchsystem.service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/24
 */
public interface MatchingService {
    String addPlayer(Integer userId, Integer rating, Integer botId, Integer gameType);

    String removePlayer(Integer userId);
}
