package com.kob.botrunningsystem.service;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
public interface BotService {
    String addBot(Integer userId, String botCode, List<Integer> ownBoard, List<Integer> otherBoard, Integer figure);
}
