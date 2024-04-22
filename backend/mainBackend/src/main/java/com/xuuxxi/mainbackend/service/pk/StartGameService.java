package com.xuuxxi.mainbackend.service.pk;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/24
 */
public interface StartGameService {
    String startGame(Integer aId, Integer aBotId,Integer bId, Integer bBotId);
    String startSnakeGame(Integer aId, Integer aBotId,Integer bId, Integer bBotId);

    String startOjGame(Integer aId, Integer bId);
}
