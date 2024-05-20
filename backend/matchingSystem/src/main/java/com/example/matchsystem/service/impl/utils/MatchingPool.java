package com.example.matchsystem.service.impl.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/24
 */
@Component
public class MatchingPool extends Thread {
    private static List<Player> players = new ArrayList<>();
    private static RestTemplate restTemplate;
    private final ReentrantLock lock = new ReentrantLock();
    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";
    private final static String startSnakeGameUrl = "http://127.0.0.1:3000/pk/start/game/snake/";
    private final static String startOjGameUrl = "http://127.0.0.1:3000/pk/start/game/oj/";
    private final static String startOjEasyGameUrl = "http://127.0.0.1:3000/pk/start/game/oj/easy/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating, Integer botId, Integer gameType) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, botId, 0, gameType));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for (Player player : players) {
                if (!player.getUserId().equals(userId)) {
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {
        for (Player player : players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean checkMatch(Player a, Player b) {
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingDelta <= waitingTime * 10;
    }

    private void sendResult(Player a, Player b) {
        LinkedMultiValueMap<String, String> res = new LinkedMultiValueMap<>();
        res.add("a_id", a.getUserId().toString());
        res.add("a_bot_id", a.getBotId().toString());
        res.add("b_id", b.getUserId().toString());
        res.add("b_bot_id",b.getBotId().toString());

        restTemplate.postForObject(startGameUrl, res, String.class);
    }

    private void sendResultSnake(Player a, Player b) {
        LinkedMultiValueMap<String, String> res = new LinkedMultiValueMap<>();
        res.add("a_id", a.getUserId().toString());
        res.add("a_bot_id", a.getBotId().toString());
        res.add("b_id", b.getUserId().toString());
        res.add("b_bot_id",b.getBotId().toString());

        restTemplate.postForObject(startSnakeGameUrl, res, String.class);
    }

    private void sendResultOj(Player a, Player b){
        LinkedMultiValueMap<String, String> res = new LinkedMultiValueMap<>();
        res.add("a_id", a.getUserId().toString());
        res.add("b_id", b.getUserId().toString());
        res.add("rating", String.valueOf(Math.min(a.getRating(), b.getRating())));

        restTemplate.postForObject(startOjGameUrl, res, String.class);
    }

    private void sendResultEasyOj(Player a, Player b){
        LinkedMultiValueMap<String, String> res = new LinkedMultiValueMap<>();
        res.add("a_id", a.getUserId().toString());
        res.add("b_id", b.getUserId().toString());

        restTemplate.postForObject(startOjEasyGameUrl, res, String.class);
    }

    private void matchAll() {
        System.out.println("match info " + players.toString());
        boolean[] used = new boolean[players.size()];
        for (int i = 0; i < players.size(); i++) {
            if (used[i]) continue;

            for (int j = i + 1; j < players.size(); j++) {
                if (used[j]) continue;
                Player a = players.get(i), b = players.get(j);
                if (checkMatch(a, b)) {
                    used[i] = used[j] = true;
                    if(a.getGameType() == 1) sendResult(a, b);
                    else if(a.getGameType() == 2) sendResultSnake(a, b);
                    else if(a.getGameType() == 3) sendResultOj(a, b);
                    else sendResultEasyOj(a, b);
                    break;
                }
            }
        }

        ArrayList<Player> newP = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!used[i]) newP.add(players.get(i));
        }

        players = newP;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchAll();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
