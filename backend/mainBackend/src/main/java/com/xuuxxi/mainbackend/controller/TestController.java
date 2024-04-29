package com.xuuxxi.mainbackend.controller;

import com.mysql.cj.util.TestUtils;
import com.xuuxxi.mainbackend.controller.oj.utils.JudgeUtils;
import com.xuuxxi.mainbackend.controller.oj.utils.TestPack;
import com.xuuxxi.mainbackend.controller.oj.utils.TestResult;
import com.xuuxxi.mainbackend.mapper.OjQuestionSampleMapper;
import com.xuuxxi.mainbackend.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
@RestController
public class TestController {
    @Autowired
    private JudgeUtils judgeUtils;

    @PostMapping("/judge")
    public void testJudge(@RequestParam MultiValueMap<String, String> data){
        TestPack testPack = new TestPack();
        testPack.setCode(Objects.requireNonNull(data.getFirst("code")));
        testPack.setUid(Objects.requireNonNull(data.getFirst("uid")));
        testPack.setQid(Long.parseLong(Objects.requireNonNull(data.getFirst("qid"))));
        testPack.setUserName(Objects.requireNonNull(data.getFirst("userName")));
        testPack.setQuestionName(Objects.requireNonNull(data.getFirst("questionName")));
        testPack.setLanguage(Integer.parseInt(Objects.requireNonNull(data.getFirst("language"))));

        TestResult testResult = judgeUtils.doJudge(testPack);
        System.out.println(testResult);
    }
}
