package com.xuuxxi.mainbackend.consumer;


import com.alibaba.fastjson.JSONObject;
import com.xuuxxi.mainbackend.consumer.utils.Game;
import com.xuuxxi.mainbackend.consumer.utils.JwtAuthentication;
import com.xuuxxi.mainbackend.consumer.utils.snake.SnakeGame;
import com.xuuxxi.mainbackend.mapper.BotMapper;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.Bot;
import com.xuuxxi.mainbackend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    // 存放当前User
    private User user;
    // 存放当前会话Session
    private Session session = null;
    // 存放User对应的Socket
    final public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    // 存放当前所有的匹配对象

    // 不是单例模式，所以不能 @AutoWired 直接注入 UserMapper
    public static UserMapper userMapper;

    public static RestTemplate restTemplate;
    public static BotMapper botMapper;

    public Game game = null;
    public SnakeGame snakeGame = null;
    private final static String addPlayerUrl = "http://127.0.0.1:3001/player/add/";
    private final static String removePlayerUrl = "http://127.0.0.1:3001/player/remove/";

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        WebSocketServer.restTemplate = restTemplate;
    }

    @Autowired
    public void setBotMapper(BotMapper botMapper) {
        WebSocketServer.botMapper = botMapper;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        System.out.println("connected!");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if (this.user != null) {
            users.put(userId, this);
        } else {
            this.session.close();
        }

        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnected!");
        if (this.user != null) {
            users.remove(this.user.getId());
        }
    }

    public static void startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        //人机加入会话
        if(bId == 114514){
            users.put(bId, users.get(aId));
        }

        User a = userMapper.selectById(aId), b = userMapper.selectById(bId);
        Bot aBot = botMapper.selectById(aBotId), bBot = botMapper.selectById(bBotId);

        Game game = new Game(a.getId(), aBot, b.getId(), bBot);

        if (users.get(a.getId()) != null)
            users.get(a.getId()).game = game;
        if (users.get(b.getId()) != null)
            users.get(b.getId()).game = game;

        game.start();

        JSONObject respGame = new JSONObject();
        respGame.put("aMap", game.getAMap());
        respGame.put("bMap", game.getBMap());
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("a_rating", a.getRating());
        respGame.put("b_rating", b.getRating());
        respGame.put("step", 0);
        respGame.put("dice_num", 0);

        JSONObject respA = new JSONObject();
        respA.put("event", "match_success");
        respA.put("opponent_username", b.getUsername());
        respA.put("opponent_photo", b.getPhoto());
        respA.put("game", respGame);
        if (users.get(a.getId()) != null)
            users.get(a.getId()).sendMsg(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "match_success");
        respB.put("opponent_username", a.getUsername());
        respB.put("opponent_photo", a.getPhoto());
        respB.put("game", respGame);
        if (users.get(b.getId()) != null && b.getId() != 114514)
            users.get(b.getId()).sendMsg(respB.toJSONString());
    }

    public static void startSnakeGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        User a = userMapper.selectById(aId), b = userMapper.selectById(bId);
        Bot aBot = botMapper.selectById(aBotId), bBot = botMapper.selectById(bBotId);

        SnakeGame game = new SnakeGame(13, 14, 20, a.getId(), aBot, b.getId(), bBot);
        game.createMap();
        if (users.get(a.getId()) != null)
            users.get(a.getId()).snakeGame = game;
        if (users.get(b.getId()) != null)
            users.get(b.getId()).snakeGame = game;

        game.start();

        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "match_success");
        respA.put("opponent_username", b.getUsername());
        respA.put("opponent_photo", b.getPhoto());
        respA.put("game", respGame);
        if (users.get(a.getId()) != null)
            users.get(a.getId()).sendMsg(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "match_success");
        respB.put("opponent_username", a.getUsername());
        respB.put("opponent_photo", a.getPhoto());
        respB.put("game", respGame);
        if (users.get(b.getId()) != null)
            users.get(b.getId()).sendMsg(respB.toJSONString());
    }

    private void startMatching(String botId) {
        System.out.println("start matching!");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        data.add("bot_id", botId);
        restTemplate.postForObject(addPlayerUrl, data, String.class);
    }

    private void stopMatching() {
        System.out.println("stop matching");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
    }

    private void move(int direction) {
        if (game.getStep() == 0 && game.getPlayerA().getId().equals(user.getId())) {
            if(game.getPlayerA().getBotId().equals(-1))
                game.setNextStepA(direction);
        } else if (game.getStep() == 1 && game.getPlayerB().getId().equals(user.getId())) {
            if(game.getPlayerB().getBotId().equals(-1))
                game.setNextStepB(direction);
        }
    }

    private void snakeMove(int direction) {
        if (snakeGame.getPlayerA().getId().equals(user.getId())) {
            if(snakeGame.getPlayerA().getBotId().equals(-1))
                snakeGame.setNextStepA(direction);
        } else if (snakeGame.getPlayerB().getId().equals(user.getId())) {
            if(snakeGame.getPlayerB().getBotId().equals(-1))
                snakeGame.setNextStepB(direction);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {  // 当做路由
        System.out.println("receive message!");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if ("start_matching".equals(event)) {
            startMatching(data.getString("bot_id"));
        } else if ("stop_matching".equals(event)) {
            stopMatching();
        } else if ("move".equals(event)) {
            move(data.getInteger("direction"));
        } else if ("roll".equals(event)){
            game.roll();
        } else if ("startBot".equals(event)){
            game.startBot(data.getString("user_id"));
        } else if ("stopBot".equals(event)){
            game.stopBot(data.getString("user_id"));
        } else if ("snakeMove".equals(event)) {
            snakeMove(data.getInteger("direction"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMsg(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}