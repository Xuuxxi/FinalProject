package com.xuuxxi.mainbackend.controller.oj.utils;

import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.consumer.utils.Game;
import com.xuuxxi.mainbackend.consumer.utils.OjGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
@RestController
public class JudgeController {
    @Autowired
    private JudgeUtils judgeUtils;

    @PostMapping("/judge/")
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
        int userId = Integer.parseInt(testPack.getUid());
        if (WebSocketServer.users.get(Integer.parseInt(testPack.getUid())) != null) {
            OjGame game = WebSocketServer.users.get(userId).ojGame;
            game.getCodeRes(testResult);
        }
    }
}
