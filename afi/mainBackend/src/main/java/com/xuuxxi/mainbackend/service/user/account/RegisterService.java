package com.xuuxxi.mainbackend.service.user.account;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/9
 */
import java.util.Map;

public interface RegisterService {
    public Map<String, String> register(String username, String password, String confirmedPassword);
}
