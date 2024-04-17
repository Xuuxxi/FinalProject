package com.kob.botrunningsystem.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotSnake {
    Integer userId;
    String botCode;
    String input;
}
