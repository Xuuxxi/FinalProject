package com.xuuxxi.mainbackend.controller.oj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xuuxxi.mainbackend.pojo.oj.OjQuestion;
import com.xuuxxi.mainbackend.service.oj.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/23
 */
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/getQuestion/")
    public OjQuestion getQuestion(){
        return questionService.getRandomOne();
    }
}
