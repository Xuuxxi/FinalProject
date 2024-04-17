package com.xuuxxi.mainbackend.controller.user.account;

import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Xuuxxi
 * @Date: 2022/9/29
 */
@RestController
public class UserPhotoController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/user/photo/")
    public Map updPhoto(@RequestParam Map<String, String> map){
        Map<String, String> res = new HashMap<>();
        String userPhoto = map.get("userPhoto");
        Integer userId = Integer.parseInt(map.get("userId"));

        User user = userMapper.selectById(userId);
        user.setPhoto(userPhoto);
        userMapper.updateById(user);

        res.put("error_message", "success");
        res.put("userPhoto", userPhoto);

        return res;
    }
}
