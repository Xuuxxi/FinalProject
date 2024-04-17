package com.kob.botrunningsystem.service.utils;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
public interface BotAi {
    Integer nextStep(List<Integer> ownBoard,List<Integer> otherBoard,Integer figure);
}
