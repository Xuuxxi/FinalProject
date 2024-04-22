package com.xuuxxi.mainbackend.consumer.utils;

import com.alibaba.fastjson.JSONObject;
import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.Bot;
import com.xuuxxi.mainbackend.pojo.User;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

//所有有先后差别的操作都要加锁
public class Game extends Thread {
    final private Player playerA, playerB;

    private Integer[] aMap, bMap;

    private Integer dice_num = 0;

    private Integer step = 0;

    private Integer stepA = null;

    private Integer stepB = null;

    private ReentrantLock lock = new ReentrantLock();


    public Player getPlayerA() {
        return this.playerA;
    }

    public Player getPlayerB() {
        return this.playerB;
    }

    public void setNextStepA(Integer nextStep) {
        stepA = nextStep;
    }

    public void setNextStepB(Integer nextStep) {
        stepB = nextStep;
    }

    public Integer getStep() {
        return step;
    }

    public Integer[] getAMap() {
        return aMap;
    }

    public Integer[] getBMap() {
        return bMap;
    }

    private String status = "playing";

    private String loser = null;

    private final static String addBotUrl = "http://127.0.0.1:3002/bot/add/";

    public Game(Integer id_a, Bot aBot, Integer id_b, Bot bBot) {
        aMap = new Integer[9];
        bMap = new Integer[9];
        for (int i = 0; i < 9; i++) aMap[i] = bMap[i] = 0;

        step = 0;

        Integer botIdA = -1, botIdB = -1;
        String botCodeA = "", botCodeB = "";

        if (aBot != null) {
            botIdA = aBot.getId();
            botCodeA = aBot.getContent();
        }

        if (bBot != null) {
            botIdB = bBot.getId();
            botCodeB = bBot.getContent();
        }

        playerA = new Player(id_a, botIdA, botCodeA, 0);
        playerB = new Player(id_b, botIdB, botCodeB, 0);
    }


    private void setBotCode(Player player) {
        if (player.getBotId().equals(-1)) return;
        if ((player.getId().equals(playerA.getId()) && step == 0) || (player.getId().equals(playerB.getId()) && step == 1))
            roll();

        //aMap == ownBoard  bMap = otherBoard
        LinkedMultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", player.getId().toString());
        data.add("bot_code", player.getBotCode());

        if (player.getId().equals(playerA.getId()) && step == 0) {
            data.add("aMap", Arrays.toString(aMap));
            data.add("bMap", Arrays.toString(bMap));
            data.add("figure", dice_num.toString());
            WebSocketServer.restTemplate.postForObject(addBotUrl, data, String.class);
        } else if (player.getId().equals(playerB.getId()) && step == 1) {
            data.add("bMap", Arrays.toString(aMap));
            data.add("aMap", Arrays.toString(bMap));
            data.add("figure", dice_num.toString());
            WebSocketServer.restTemplate.postForObject(addBotUrl, data, String.class);
        }
    }

    //投骰子
    public void roll() {
        Random random = new Random();
        if (dice_num == 0) dice_num = random.nextInt(6) + 1;

        JSONObject resp = new JSONObject();
        resp.put("event", "roll");
        resp.put("dice_num", dice_num);

        sendAllMsg(resp.toJSONString());
    }

    //检查下一步操作并切换当前操作用户
    private boolean nextStep() {
        //最快两毫秒操作一次
        //给 200 * 100 时间输入
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setBotCode(playerA);
        setBotCode(playerB);

        for (int i = 0; i < 200; i++) {
            try {
                Thread.sleep(100);

                if (step == 0 && stepA != null) return true;
                if (step == 1 && stepB != null) return true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    //检查消除
    private void setMap() {
        try {
            lock.lock();
            Integer num = dice_num;
            if (step == 0) {
                Integer pos = stepA;
                aMap[pos] = num;
                for (int i = pos / 3 * 3; i < pos / 3 * 3 + 3; i++) if (bMap[i].equals(num)) bMap[i] = 0;
            } else {
                Integer pos = stepB;
                bMap[pos] = num;
                for (int i = pos / 3 * 3; i < pos / 3 * 3 + 3; i++) if (aMap[i].equals(num)) aMap[i] = 0;
            }
        } finally {
            lock.unlock();
        }
    }

    //计算分数
    private int countMap(Integer[] map) {
        int score = 0;

        for (int i = 0; i < 3; i++) {
            int a = map[i * 3];
            int b = map[i * 3 + 1];
            int c = map[i * 3 + 2];

            if (a == b && b == c) score += a * 9;
            else if (a == b) score += a * 4 + c;
            else if (a == c) score += a * 4 + b;
            else if (b == c) score += b * 4 + a;
            else score += (a + b + c);
        }

        return score;
    }

    //判断胜负
    private void judge_loser() {
        int a_score = countMap(aMap);
        int b_score = countMap(bMap);

        if (a_score > b_score) loser = "B";
        else if (a_score < b_score) loser = "A";
        else loser = "all";
        System.out.println("loser = " + loser + " aS = " + a_score + " bS = " + b_score);

        getPlayerA().setScore(a_score);
        getPlayerB().setScore(b_score);

        status = "finished";
    }

    //判断是否结束
    private void judge_full() {
        int cnt = 0;

        if (step == 0) {
            for (int i = 0; i < 9; i++) if (aMap[i] != 0) cnt++;
        } else {
            for (int i = 0; i < 9; i++) if (bMap[i] != 0) cnt++;
        }

        if (cnt == 9) {
            judge_loser();
        }
    }

    //开启托管
    public void startBot(String userId){
        Integer curId = Integer.parseInt(userId);
        System.out.println("userId = " + curId + " 开启托管");
        if(playerA.getId().equals(curId)){
            playerA.setBotId(114514);
            playerA.setBotCode(WebSocketServer.botMapper.selectById(114514).getContent());
            setBotCode(playerA);
        }else if(playerB.getId().equals(curId)){
            playerB.setBotId(114514);
            playerB.setBotCode(WebSocketServer.botMapper.selectById(114514).getContent());
            setBotCode(playerB);
        }
    }

    public void stopBot(String userId){
        Integer curId = Integer.parseInt(userId);
        System.out.println("userId = " + curId + " 停止托管");
        if(playerA.getId().equals(curId)){
            playerA.setBotId(-1);
            playerA.setBotCode("");
        }else if(playerB.getId().equals(curId)){
            playerB.setBotId(-1);
            playerB.setBotCode("");
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            //没有下一步操作或者下一步操作不合法都会导致游戏结束
            if (nextStep()) {
                //执行下一步操作
                setMap();
                judge_full();
                sendCurMap();

                if (!status.equals("playing")) {
                    sendResult();
                    break;
                }
            } else {
                if (step == 0) loser = "A";
                else loser = "B";

                sendResult();
                break;
            }
        }
    }

    //向两个用户广播
    private void sendAllMsg(String msg) {
        if (WebSocketServer.users.get(playerA.getId()) != null)
            WebSocketServer.users.get(playerA.getId()).sendMsg(msg);
        if (WebSocketServer.users.get(playerB.getId()) != null && playerB.getId() != 114514)
            WebSocketServer.users.get(playerB.getId()).sendMsg(msg);
    }

    private void RatingUpdate() {
        //赢了 + 5分，输了 - 5分。
        if (playerB.getId() == 114514) return;
        UserMapper userMapper = WebSocketServer.userMapper;

        if ("B".equals(loser)) {
            User userA = userMapper.selectById(playerA.getId());
            userA.setRating(userA.getRating() + 5);
            userMapper.updateById(userA);

            User userB = userMapper.selectById(playerB.getId());
            userB.setRating(userB.getRating() - 5);
            userMapper.updateById(userB);
        }

        if ("A".equals(loser)) {
            User userA = userMapper.selectById(playerA.getId());
            userA.setRating(userA.getRating() - 5);
            userMapper.updateById(userA);

            User userB = userMapper.selectById(playerB.getId());
            userB.setRating(userB.getRating() + 5);
            userMapper.updateById(userB);
        }
    }

    //广播结果
    private void sendResult() {
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        resp.put("a_score", playerA.getScore());
        resp.put("b_score", playerB.getScore());
        RatingUpdate();

        sendAllMsg(resp.toJSONString());

        if (playerB.getId() == 114514) {
            WebSocketServer.users.remove(114514);
        }
    }

    //广播移动
    private void sendCurMap() {
        stepA = stepB = null;
        dice_num = 0;
        step = (step + 1) % 2;
        JSONObject resp = new JSONObject();
        resp.put("event", "curMap");
        resp.put("aMap", aMap);
        resp.put("bMap", bMap);
        resp.put("step", step);
        resp.put("dice_num", dice_num);
        sendAllMsg(resp.toJSONString());
    }
}
