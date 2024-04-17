package com.kob.botrunningsystem.service.utils;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/15
 */
public interface BotSnakeAi {
    //格式：地图#我的横坐标#我的纵坐标#我的操作#对手的横坐标#对手的纵坐标#对手的操作
    Integer nextMove(String input);
}
