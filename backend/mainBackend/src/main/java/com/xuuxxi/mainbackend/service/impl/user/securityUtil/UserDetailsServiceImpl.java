package com.xuuxxi.mainbackend.service.impl.user.securityUtil;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/6
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);

        User user = userMapper.selectOne(wrapper);
        if(user == null){
            throw new RuntimeException("user == null");
        }

        return new UserDetailsImpl(user);
    }
}
