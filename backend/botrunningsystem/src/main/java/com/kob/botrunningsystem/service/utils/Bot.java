package com.kob.botrunningsystem.service.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/25
 */
public class Bot implements com.kob.botrunningsystem.service.utils.BotAi {

    private Integer count(List<Integer> ownBoard, List<Integer> otherBoard, Integer figure, Integer pos) {
        Integer[] aMap = ownBoard.toArray(new Integer[0]);
        aMap[pos] = figure;
        Integer[] bMap = otherBoard.toArray(new Integer[0]);
        for (int i = pos / 3 * 3; i < pos / 3 * 3 + 3; i++) if (bMap[i].equals(figure)) bMap[i] = 0;

        int a_score = 0;
        int b_score = 0;

        for (int i = 0; i < 3; i++) {
            int a = aMap[i * 3];
            int b = aMap[i * 3 + 1];
            int c = aMap[i * 3 + 2];

            if (a == b && b == c) a_score += a * 9;
            else if (a == b) a_score += a * 4 + c;
            else if (a == c) a_score += a * 4 + b;
            else if (b == c) a_score += b * 4 + a;
            else a_score += (a + b + c);
        }

        for (int i = 0; i < 3; i++) {
            int a = bMap[i * 3];
            int b = bMap[i * 3 + 1];
            int c = bMap[i * 3 + 2];

            if (a == b && b == c) b_score += a * 9;
            else if (a == b) b_score += a * 4 + c;
            else if (a == c) b_score += a * 4 + b;
            else if (b == c) b_score += b * 4 + a;
            else b_score += (a + b + c);
        }

        return a_score - b_score;
    }

    private Integer getAns(List<Integer> ownBoard, List<Integer> otherBoard, Integer figure, List<Integer> res) {
        if (res.isEmpty()) return -1;
        int max = -999;
        int ans = 0;

        for (int i = 0; i < res.size(); i++) {
            int tmp = count(ownBoard, otherBoard, figure, res.get(i));

            if (max < tmp) {
                max = tmp;
                ans = i;
            }
        }

        return res.get(ans);
    }

    @Override
    public Integer nextStep(List<Integer> ownBoard, List<Integer> otherBoard, Integer figure) {
        int res = -1;
        ArrayList<Integer> tmpRes = new ArrayList<>();
        //遍历三层
        //有无相同数字
        for (int pos = 0; pos < 3; pos++) {
            //单独遍历每一层、找有无相同数字
            for (int j = pos * 3; j < pos * 3 + 3; j++) {
                //找到相同数字、能否放入
                if (ownBoard.get(j).equals(figure)) {
                    for (int i = pos * 3; i < pos * 3 + 3; i++) {
                        //这一层有无地方放
                        if (ownBoard.get(i).equals(0)) {
                            tmpRes.add(i);
                        }
                    }
                }
            }
        }

        res = getAns(ownBoard, otherBoard, figure, tmpRes);
        if (res != -1) return res;

        int[] cnt = {0, 0, 0};
        int[] num = {0, 0, 0};
        int min = 999;
        int noNullMin = 0;
        //无相同
        for (int pos = 0; pos < 3; pos++) {
            for (int j = pos * 3; j < pos * 3 + 3; j++) {
                num[pos] += ownBoard.get(j);
                if (!ownBoard.get(j).equals(0)) cnt[pos]++;
            }
        }

        for (int pos = 0; pos < 3; pos++) {
            //有空层
            if (cnt[pos] == 0) {
                tmpRes.add(pos * 3);
            }

            if (min > num[pos] && cnt[pos] < 3) {
                min = num[pos];
                noNullMin = pos;
            }
        }

        res = getAns(ownBoard, otherBoard, figure, tmpRes);
        if (res != -1) return res;


        for (int i = noNullMin * 3; i < noNullMin * 3 + 3; i++) {
            if (ownBoard.get(i).equals(0)) tmpRes.add(i);
        }

        res = getAns(ownBoard, otherBoard, figure, tmpRes);
        return res;
    }
}