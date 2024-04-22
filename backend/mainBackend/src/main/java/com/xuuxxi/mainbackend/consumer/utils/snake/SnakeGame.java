package com.xuuxxi.mainbackend.consumer.utils.snake;


import com.alibaba.fastjson.JSONObject;
import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.Bot;
import com.xuuxxi.mainbackend.pojo.User;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


//所有有先后差别的操作都要加锁
public class SnakeGame extends Thread {
    final private Integer rows;
    final private Integer cols;
    final private Integer inner_walls_count;
    final private int[][] g;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    final private SnakePlayer playerA, playerB;

    private Integer stepA = null;

    private Integer stepB = null;

    private ReentrantLock lock = new ReentrantLock();


    public SnakePlayer getPlayerA() {
        return this.playerA;
    }

    public SnakePlayer getPlayerB() {
        return this.playerB;
    }

    public void setNextStepA(Integer nextStep) {
        stepA = nextStep;
    }

    public void setNextStepB(Integer nextStep) {
        stepB = nextStep;
    }

    private String status = "playing";

    private String loser = null;

    private final static String addBotUrl = "http://127.0.0.1:3002/bot/add/snake/";

    public SnakeGame(Integer rows, Integer cols, Integer inner_walls_count, Integer id_a, Bot aBot, Integer id_b, Bot bBot) {
        this.rows = rows;
        this.cols = cols;
        this.inner_walls_count = inner_walls_count;
        this.g = new int[rows][cols];

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
        playerA = new SnakePlayer(id_a, botIdA, botCodeA, rows - 2, 1, new ArrayList<>());
        playerB = new SnakePlayer(id_b, botIdB, botCodeB, 1, cols - 2, new ArrayList<>());
    }

    public int[][] getG() {
        return g;
    }

    private boolean check_connectivity(int sx, int sy, int tx, int ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = 1;

        for (int i = 0; i < 4; i++) {
            int x = sx + dx[i], y = sy + dy[i];
            if (x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0) {
                if (check_connectivity(x, y, tx, ty)) {
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }

        g[sx][sy] = 0;
        return false;
    }

    //开启托管
    public void startBot(String userId){
        Integer curId = Integer.parseInt(userId);
        System.out.println("userId = " + curId + " 开启托管");
        if(playerA.getId().equals(curId)){
            playerA.setBotId(114515);
            playerA.setBotCode(WebSocketServer.botMapper.selectById(114515).getContent());
            setBotCode(playerA);
        }else if(playerB.getId().equals(curId)){
            playerB.setBotId(114515);
            playerB.setBotCode(WebSocketServer.botMapper.selectById(114515).getContent());
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

    private boolean draw() {  // 画地图
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                g[i][j] = 0;
            }
        }

        for (int r = 0; r < this.rows; r++) {
            g[r][0] = g[r][this.cols - 1] = 1;
        }
        for (int c = 0; c < this.cols; c++) {
            g[0][c] = g[this.rows - 1][c] = 1;
        }

        Random random = new Random();
        for (int i = 0; i < this.inner_walls_count / 2; i++) {
            for (int j = 0; j < 1000; j++) {
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if (g[r][c] == 1 || g[this.rows - 1 - r][this.cols - 1 - c] == 1)
                    continue;
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2)
                    continue;

                g[r][c] = g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }

        return check_connectivity(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void createMap() {
        for (int i = 0; i < 1000; i++) {
            if (draw())
                break;
        }
    }

    private String getInput(SnakePlayer player) {
        SnakePlayer me, you;
        if (playerA.getId().equals(player.getId())) {
            me = playerA;
            you = playerB;
        } else {
            me = playerB;
            you = playerA;
        }

        return getMapString() + "#" +
                me.getSx() + "#" +
                me.getSy() + "#(" +
                me.getStepsString() + ")#" +
                you.getSx() + "#" +
                you.getSy() + "#(" +
                you.getStepsString() + ")";
    }

    private void setBotCode(SnakePlayer player) {
        if (player.getBotId().equals(-1)) return;
        LinkedMultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", player.getId().toString());
        data.add("bot_code", player.getBotCode());
        data.add("input", getInput(player));
        WebSocketServer.restTemplate.postForObject(addBotUrl, data, String.class);
    }

    private boolean nextStep() {
        //前端规定一秒渲染五个格子，所以至少要 200ms 才可以进行下一次操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setBotCode(playerA);
        setBotCode(playerB);

        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
                lock.lock();

                try {
                    if (stepA != null && stepB != null) {
                        playerA.getSteps().add(stepA);
                        playerB.getSteps().add(stepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    private void judge() {
        List<Cell> cellsA = playerA.getCells();
        List<Cell> cellsB = playerB.getCells();

        boolean validA = checkValid(cellsA, cellsB);
        boolean validB = checkValid(cellsB, cellsA);

        if (!validB || !validA) {
            status = "finished";
            System.out.println("不合法移动！");

            if (!validA && !validB) loser = "all";
            else if (!validA) loser = "A";
            else loser = "B";
        }
    }

    private boolean checkValid(List<Cell> cellsA, List<Cell> cellsB) {
        int len = cellsA.size();
        Cell cell = cellsA.get(len - 1);
        if (g[cell.getX()][cell.getY()] == 1) return false;

        for (int i = 0; i < len - 1; i++) {
            if (cellsA.get(i).getX() == cell.getX() && cellsA.get(i).getY() == cell.getY()) return false;
        }

        for (int i = 0; i < cellsB.size(); i++) {
            if (cell.getX() == cellsB.get(i).getX() && cell.getY() == cellsB.get(i).getY()) return false;
        }

        return true;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            //没有下一步操作或者下一步操作不合法都会导致游戏结束
            if (nextStep()) {
                judge();
                if (status.equals("playing")) {
                    sendMove();
                } else {
                    sendResult();
                    break;
                }
            } else {
                System.out.println("没有下一步！");
                if (stepA == null && stepB == null) loser = "all";
                else if (stepA == null) loser = "A";
                else loser = "B";

                sendResult();
                break;
            }
        }
    }

    private void sendAllMsg(String msg) {
        if (WebSocketServer.users.get(playerA.getId()) != null)
            WebSocketServer.users.get(playerA.getId()).sendMsg(msg);
        if (WebSocketServer.users.get(playerB.getId()) != null)
            WebSocketServer.users.get(playerB.getId()).sendMsg(msg);

        System.out.println(msg);
    }



    private void RatingUpdate() {
        //赢了 + 5分，输了 - 5分。
        if (playerB.getId() == 114515) return;
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

    private void sendResult() {
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        RatingUpdate();

        sendAllMsg(resp.toJSONString());
    }

    private void sendMove() {
        try {
            lock.lock();
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("stepA", stepA);
            resp.put("stepB", stepB);
            sendAllMsg(resp.toJSONString());
            stepA = stepB = null;
        } finally {
            lock.unlock();
        }
    }

    private String getMapString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }
}
