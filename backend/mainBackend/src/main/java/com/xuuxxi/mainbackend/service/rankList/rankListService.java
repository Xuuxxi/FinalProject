package com.xuuxxi.mainbackend.service.rankList;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/29
 */
public interface rankListService {
    JSONObject getList(Integer page);
}

