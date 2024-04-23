package com.xuuxxi.mainbackend.pojo.oj;

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
    private Long id;
    private Long qid;
    private String questionName;
    private String sampleInput;
    private String sampleOutput;
}
