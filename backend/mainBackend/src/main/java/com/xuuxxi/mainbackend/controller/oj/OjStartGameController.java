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
        OjQuestion one = questionService.getRandomOne();

        return startGameService.startOjGame(aId, bId, one.getId());
    }
}
