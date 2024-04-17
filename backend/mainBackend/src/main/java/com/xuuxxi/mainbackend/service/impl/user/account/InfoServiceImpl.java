package com.xuuxxi.mainbackend.service.impl.user.account;


import com.xuuxxi.mainbackend.pojo.User;
import com.xuuxxi.mainbackend.service.impl.user.securityUtil.UserDetailsImpl;
import com.xuuxxi.mainbackend.service.user.account.InfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/9
 */
@Service
public class InfoServiceImpl implements InfoService {
    @Override
    public Map<String, String> getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "success");
        map.put("id", user.getId().toString());
        map.put("username", user.getUsername());
        map.put("photo", user.getPhoto());
        return map;
    }
}
