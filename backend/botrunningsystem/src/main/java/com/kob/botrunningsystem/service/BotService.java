package com.kob.botrunningsystem.service;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
public interface BotService {
    String addBotSnake(Integer userId, String botCode, String input);
    String addBot(Integer userId, String botCode, List<Integer> ownBoard, List<Integer> otherBoard, Integer figure);
}
