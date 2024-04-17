package com.xuuxxi.mainbackend.controller.user.bot;


import com.xuuxxi.mainbackend.pojo.Bot;
import com.xuuxxi.mainbackend.service.user.bot.BotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/14
 */
@RestController
public class BotController {
    @Resource
    private BotService botService;

    @PostMapping("/user/bot/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return botService.add(data);
    }

    @GetMapping("/user/bot/getlist/")
    public List<Bot> getList() {
        return botService.getList();
    }

    @PostMapping("/user/bot/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return botService.remove(data);
    }

    @PostMapping("/user/bot/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return botService.update(data);
    }
}
