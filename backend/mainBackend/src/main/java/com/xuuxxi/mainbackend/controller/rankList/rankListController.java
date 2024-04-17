package com.xuuxxi.mainbackend.controller.rankList;

import com.alibaba.fastjson.JSONObject;
import com.xuuxxi.mainbackend.service.rankList.rankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/29
 */
@RestController
public class rankListController {
    @Autowired
    private rankListService getRankListService;

    @GetMapping("/ranklist/getlist/")
    public JSONObject getList(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return getRankListService.getList(page);
    }
}

