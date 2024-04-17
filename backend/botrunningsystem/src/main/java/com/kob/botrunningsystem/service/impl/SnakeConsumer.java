package com.kob.botrunningsystem.service.impl;

import com.kob.botrunningsystem.service.utils.BotSnakeAi;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/15
 */
@Component
public class SnakeConsumer extends Thread{
    private BotSnake botSnake;
    private static RestTemplate restTemplate;
    private final static String receiveSnakeBotMoveUrl = "http://127.0.0.1:3000/pk/receive/bot/move/snake/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        SnakeConsumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, BotSnake bot) {
        this.botSnake = bot;
        this.start();

        try {
            this.join(timeout);  // 最多等待timeout秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();  // 终端当前线程
        }
    }

    private String addUid(String code, String uid) {  // 在code中的Bot类名后添加uid
        int k = code.indexOf(" implements com.kob.botrunningsystem.service.utils.BotSnakeAi");
        return code.substring(0, k) + uid + code.substring(k);
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);

        BotSnakeAi botSnakeInterface = Reflect.compile(
                "com.kob.botrunningsystem.service.utils.BotSnake" + uid,
                addUid(botSnake.getBotCode(), uid)
        ).create().get();

        Integer direction = botSnakeInterface.nextMove(botSnake.getInput());

        System.out.println("move-direction: " + botSnake.getUserId() + " " + direction);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", botSnake.getUserId().toString());
        data.add("direction", direction.toString());

        restTemplate.postForObject(receiveSnakeBotMoveUrl, data, String.class);
    }
}
