package com.xuuxxi.mainbackend.service.user.bot;


import com.xuuxxi.mainbackend.pojo.Bot;

import java.util.List;
import java.util.Map;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/14
 */
public interface BotService {
    Map<String, String> add(Map<String, String> data);

    List<Bot> getList();

    Map<String, String> remove(Map<String, String> data);

    Map<String, String> update(Map<String, String> data);
}
