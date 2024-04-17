package com.kob.botrunningsystem.botrunningsystem;

import com.kob.botrunningsystem.service.utils.Bot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/10/5
 */


public class testBot {
    public static void main(String[] args) {
        Bot bot = new Bot();
        List<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();


        for(int i = 0;i < 3;i ++) b.add(0);
        for(int i = 3;i < 6;i ++) b.add(4);
        for(int i = 6;i < 9;i ++) b.add(0);

        for(int i = 0;i < 9;i ++) a.add(0);

        System.out.println(bot.nextStep(a,b,4));
    }
}
