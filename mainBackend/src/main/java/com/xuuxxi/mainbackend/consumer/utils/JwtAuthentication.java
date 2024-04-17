package com.xuuxxi.mainbackend.consumer.utils;


import com.xuuxxi.mainbackend.utils.JwtUtil;
import io.jsonwebtoken.Claims;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/17
 */
public class JwtAuthentication {
    public static Integer getUserId(String token) {
        int userId = -1;
        try {
            //获取jwt
            Claims claims = JwtUtil.parseJWT(token);
            //获取userId
            userId = Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userId;
    }
}
