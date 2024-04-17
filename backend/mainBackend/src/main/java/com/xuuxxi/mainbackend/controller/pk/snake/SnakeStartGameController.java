package com.xuuxxi.mainbackend.controller.pk.snake;

import com.xuuxxi.mainbackend.service.pk.StartGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/24
 */
@RestController
public class SnakeStartGameController {
    @Autowired
    private StartGameService startGameService;

    @PostMapping("/pk/start/game/snake/")
    public String startGame(@RequestParam MultiValueMap<String, String> data) {
        Integer aId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_id")));
        Integer aBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("a_bot_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_id")));
        Integer bBotId = Integer.parseInt(Objects.requireNonNull(data.getFirst("b_bot_id")));

        return startGameService.startSnakeGame(aId, aBotId, bId, bBotId);
    }
}