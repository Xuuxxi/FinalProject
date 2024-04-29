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
public class OjQuestionSample {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long qid;
    private String questionName;
    private String sampleInput;
    private String sampleOutput;
}
