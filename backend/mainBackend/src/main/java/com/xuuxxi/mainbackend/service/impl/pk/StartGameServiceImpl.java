package com.xuuxxi.mainbackend.service.impl.pk;

import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/24
 */
@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        WebSocketServer.startGame(aId, aBotId, bId, bBotId);
        return "start game success";
    }

    @Override
    public String startSnakeGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        WebSocketServer.startSnakeGame(aId , aBotId, bId, bBotId);
        return "start game success";
    }
}
