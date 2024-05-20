package com.xuuxxi.mainbackend.controller.oj;

import com.xuuxxi.mainbackend.pojo.oj.OjQuestion;
import com.xuuxxi.mainbackend.service.oj.QuestionService;
import com.xuuxxi.mainbackend.service.pk.StartGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/22
 */
@RestController
public class OjStartGameController {
    @Autowired
    private StartGameService startGameService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/pk/start/game/oj/")
    public String startGame(@RequestParam MultiValueMap<String, String> data) {
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        int rating = Integer.parseInt(Objects.requireNonNull(data.getFirst("rating")));
        String dif;
        if(rating <= 1700) dif = "简单";
        else if(rating <= 2000) dif = "中等";
        else if(rating <= 2200) dif = "困难";
        else dif = "噩梦";
        OjQuestion one = questionService.getRandomOne(dif);

        return startGameService.startOjGame(aId, bId, one.getId());
    }

    @PostMapping("/pk/start/game/oj/easy/")
    public String startGameEasy(@RequestParam MultiValueMap<String, String> data) {
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        OjQuestion one = questionService.getById("440640587125886976");

        return startGameService.startOjGame(aId, bId, one.getId());
    }
}
