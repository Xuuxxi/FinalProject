package com.kob.botrunningsystem.service.impl;


import com.kob.botrunningsystem.service.BotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
@Service
public class BotServiceImpl implements BotService {
    public final static BotPool botPool = new BotPool();
    public final static SnakeBotPool botSnakePool = new SnakeBotPool();
    @Override
    public String addBot(Integer userId, String botCode, List<Integer> ownBoard, List<Integer> otherBoard, Integer figure) {

        botPool.addBot(userId, botCode, ownBoard, otherBoard, figure);
        return "add bot success!";
    }

    @Override
    public String addBotSnake(Integer userId, String botCode, String input) {
        botSnakePool.addBotSnake(userId, botCode, input);
        return "add botSnake success!";
    }
}
