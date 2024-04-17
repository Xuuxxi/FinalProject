package com.kob.botrunningsystem.service.impl;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/15
 */
public class SnakeBotPool extends Thread {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Queue<BotSnake> botSnakes = new LinkedList<>();

    public void addBotSnake(Integer userId, String botCode, String input) {
        lock.lock();
        try {
            botSnakes.add(new BotSnake(userId, botCode, input));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private void consume(BotSnake bot) {
        SnakeConsumer consumer = new SnakeConsumer();
        consumer.startTimeout(2000, bot);
    }


    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (botSnakes.isEmpty()) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else {
                BotSnake bot = botSnakes.remove();
                lock.unlock();
                consume(bot);
            }
        }
    }
}
