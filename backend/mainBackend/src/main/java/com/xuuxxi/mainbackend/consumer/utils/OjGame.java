package com.xuuxxi.mainbackend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.User;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
public class OjGame {
    private String loser = null;
    private Integer aId, bId;

    private Long questionId;

    public OjGame(Integer a, Integer b, Long questionId) {
        this.aId = a;
        this.bId = b;
        this.questionId = questionId;
    }

    public void debugCode(String code, String codeType) {
        String msg = "its ok";
        sendAllMsg(msg);
    }

    public void commitCode(String code, String codeType) {
        loser = "A";
        sendResult();

        sendAllMsg("yey");
    }

    private void sendAllMsg(String msg) {
        if (WebSocketServer.users.get(aId) != null)
            WebSocketServer.users.get(aId).sendMsg(msg);
        if (WebSocketServer.users.get(bId) != null)
            WebSocketServer.users.get(bId).sendMsg(msg);

        System.out.println(msg);
    }


    private void RatingUpdate() {
        UserMapper userMapper = WebSocketServer.userMapper;

        if ("B".equals(loser)) {
            User userA = userMapper.selectById(aId);
            userA.setRating(userA.getRating() + 5);
            userMapper.updateById(userA);

            User userB = userMapper.selectById(bId);
            userB.setRating(userB.getRating() - 5);
            userMapper.updateById(userB);
        }

        if ("A".equals(loser)) {
            User userA = userMapper.selectById(aId);
            userA.setRating(userA.getRating() - 5);
            userMapper.updateById(userA);

            User userB = userMapper.selectById(bId);
            userB.setRating(userB.getRating() + 5);
            userMapper.updateById(userB);
        }
    }

    private void sendResult() {
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        RatingUpdate();

        sendAllMsg(resp.toJSONString());
    }
}
