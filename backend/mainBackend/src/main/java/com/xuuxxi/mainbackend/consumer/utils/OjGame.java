package com.xuuxxi.mainbackend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.controller.oj.utils.JudgeUtils;
import com.xuuxxi.mainbackend.controller.oj.utils.TestPack;
import com.xuuxxi.mainbackend.controller.oj.utils.TestResult;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
public class OjGame extends Thread {
    private String loser = null;
    private Integer aId, bId;
//    private ReentrantLock lock = new ReentrantLock();
    private Long questionId;

    public OjGame(Integer a, Integer b, Long questionId) {
        this.aId = a;
        this.bId = b;
        this.questionId = questionId;
    }

    public void getCodeRes(TestResult testResult) {
        if(testResult.isPass()){
            if(Integer.parseInt(testResult.getUid()) == this.aId){
                loser="B";
                sendResult();
            }else {
                loser = "A";
                sendResult();
            }
        }

        JSONObject resp = new JSONObject();
        resp.put("event", "commitResp");
        resp.put("message", testResult.getMessage());
        resp.put("title", testResult.getTitle());
        sendAllMsg(resp.toJSONString());
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
