package com.xuuxxi.mainbackend.controller.oj.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSample {
    /**
     * 题目id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long qid;
    /**
     * 标准输入
     */
    private String input;
    /**
     * 标准输出
     */
    private String output;
    /**
     * 用户的输出
     */
    private String userOutput;
    /**
     * 是否正确
     */
    private boolean right;

}