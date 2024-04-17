package com.kob.botrunningsystem.service.impl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bot {
    Integer userId;
    String botCode;
    List<Integer> ownBoard;
    List<Integer> otherBoard;
    Integer figure;
}

