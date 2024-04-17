package com.xuuxxi.mainbackend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuuxxi.mainbackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Xuuxxi
 * @Date: 2022/8/6
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
