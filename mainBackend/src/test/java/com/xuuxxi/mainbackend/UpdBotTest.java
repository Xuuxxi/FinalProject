package com.xuuxxi.mainbackend;

import com.xuuxxi.mainbackend.mapper.BotMapper;
import com.xuuxxi.mainbackend.pojo.Bot;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/29
 */
public class UpdBotTest {
    BotMapper botMapper;

    public void TestUpd(){
        try{
            Bot curBot = botMapper.selectById(4);
            curBot.setDescription(curBot.getDescription() + " change for test");
            botMapper.updateById(curBot);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("test done");
        }
    }
}
