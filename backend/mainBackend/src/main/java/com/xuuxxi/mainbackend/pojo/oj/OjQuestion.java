package com.xuuxxi.mainbackend.pojo.oj;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OjQuestion {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String questionName;
    private String inputStyle;
    private String outputStyle;
    private String dataRange;
    private String inputSample;
    private String outputSample;
    private String difficulty;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String description;
    private Integer totalPass;
    private Integer totalAttempt;
    private String resource;
    private String tag;
    private Double passRate;
    private Integer privateState;
}
