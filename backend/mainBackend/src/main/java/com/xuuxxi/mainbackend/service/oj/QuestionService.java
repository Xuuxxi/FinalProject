package com.xuuxxi.mainbackend.service.oj;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuuxxi.mainbackend.pojo.oj.OjQuestion;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/23
 */
public interface QuestionService extends IService<OjQuestion> {
    OjQuestion getRandomOne();
}
