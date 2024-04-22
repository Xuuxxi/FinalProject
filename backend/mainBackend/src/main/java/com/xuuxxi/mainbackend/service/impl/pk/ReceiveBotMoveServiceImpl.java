package com.xuuxxi.mainbackend.service.impl.pk;


import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.consumer.utils.Game;
import com.xuuxxi.mainbackend.consumer.utils.snake.SnakeGame;
import com.xuuxxi.mainbackend.service.pk.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService {
    @Override
    public String receiveBotMove(Integer userId, Integer direction) {
        if (WebSocketServer.users.get(userId) != null) {
            Game game = WebSocketServer.users.get(userId).game;
            if (game != null) {
                if (game.getPlayerA().getId().equals(userId)) {
                    game.setNextStepA(direction);
                } else if (game.getPlayerB().getId().equals(userId)) {
                    game.setNextStepB(direction);
                }
            }
        }

        return "receive bot move success";
    }

    @Override
    public String receiveSnakeBotMove(Integer userId, Integer direction) {
        if (WebSocketServer.users.get(userId) != null) {
            SnakeGame game = WebSocketServer.users.get(userId).snakeGame;
            if (game != null) {
                if (game.getPlayerA().getId().equals(userId)) {
                    game.setNextStepA(direction);
                } else if (game.getPlayerB().getId().equals(userId)) {
                    game.setNextStepB(direction);
                }
            }
        }

        return "receive bot move success";
    }
}

