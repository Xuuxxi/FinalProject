package com.kob.botrunningsystem.controller;


import com.kob.botrunningsystem.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
@RestController
public class BotController {
    @Autowired
    private BotService botService;

    @PostMapping("/bot/add/")
    public String addBot(@RequestParam MultiValueMap<String,String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String botCode = data.getFirst("bot_code");
        String aMap = data.getFirst("aMap");
        String bMap = data.getFirst("bMap");
        Integer figure = Integer.parseInt(Objects.requireNonNull(data.getFirst("figure")));

        List<Integer> ownBoard = new ArrayList<>(),otherBoard = new ArrayList<>();

        if(aMap != null){
            aMap = aMap.substring(1,aMap.length() - 1);
            String[] tmp = aMap.split(",");
            for(String i : tmp) ownBoard.add(Integer.parseInt(i.trim()));
        }

        if(bMap != null){
            bMap = bMap.substring(1,bMap.length() - 1);
            String[] tmp = bMap.split(",");
            for(String i : tmp) otherBoard.add(Integer.parseInt(i.trim()));
        }

        return botService.addBot(userId, botCode, ownBoard, otherBoard, figure);
    }

    @PostMapping("/bot/add/snake/")
    public String addBotSnake(@RequestParam MultiValueMap<String,String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String botCode = data.getFirst("bot_code");
        String input = data.getFirst("input");
        return botService.addBotSnake(userId, botCode, input);
    }
}
