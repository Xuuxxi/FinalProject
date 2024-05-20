package com.xuuxxi.mainbackend.service.impl.oj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuuxxi.mainbackend.mapper.QuestionMapper;
import com.xuuxxi.mainbackend.pojo.oj.OjQuestion;
import com.xuuxxi.mainbackend.service.oj.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/23
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, OjQuestion> implements QuestionService {
    @Autowired QuestionMapper questionMapper;
    @Override
    public OjQuestion getRandomOne(String dif) {
        QueryWrapper<OjQuestion> queryWrapper = Wrappers.query();
        queryWrapper.eq("difficulty", dif);
        queryWrapper.orderByDesc("RAND()").last("LIMIT 1");
        return questionMapper.selectOne(queryWrapper);
    }
}
