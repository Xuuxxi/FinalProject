package com.xuuxxi.mainbackend.consumer.utils;

import javafx.scene.control.Cell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private Integer botId;
    private String botCode;
    private Integer score;
}

