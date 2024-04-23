package com.xuuxxi.mainbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuuxxi.mainbackend.pojo.oj.OjQuestion;
import com.xuuxxi.mainbackend.pojo.oj.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/23
 */
@Mapper
public interface QuestionMapper extends BaseMapper<OjQuestion> {
}
