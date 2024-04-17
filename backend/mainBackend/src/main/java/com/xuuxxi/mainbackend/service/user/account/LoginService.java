package com.xuuxxi.mainbackend.service.user.account;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/9
 */
import java.util.Map;

public interface LoginService {
    public Map<String, String> getToken(String username, String password);
}
