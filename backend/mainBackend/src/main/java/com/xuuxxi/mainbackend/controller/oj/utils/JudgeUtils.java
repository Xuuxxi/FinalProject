package com.xuuxxi.mainbackend.controller.oj.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuuxxi.mainbackend.consumer.WebSocketServer;
import com.xuuxxi.mainbackend.mapper.OjQuestionSampleMapper;
import com.xuuxxi.mainbackend.mapper.QuestionMapper;
import com.xuuxxi.mainbackend.mapper.UserMapper;
import com.xuuxxi.mainbackend.pojo.oj.OjQuestion;
import com.xuuxxi.mainbackend.pojo.oj.OjQuestionSample;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
@Component
public class JudgeUtils {
    private OjQuestionSampleMapper questionSampleMapper;

    private QuestionMapper questionMapper;

    private ContainerUtils containerUtils;

    @Autowired
    public void setOjQuestionSampleMapper(OjQuestionSampleMapper ojQuestionSampleMapper) {
        this.questionSampleMapper = ojQuestionSampleMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setContainerUtils(ContainerUtils containerUtils) {
        this.containerUtils = containerUtils;
    }

    public List<TestSample> getTestSamples(long id) {
        QueryWrapper<OjQuestionSample> wrapper = new QueryWrapper<>();
        wrapper.eq("qid", id);
        OjQuestionSample ojQuestionSample = questionSampleMapper.selectOne(wrapper);
//        json解码
        JSONArray inputs = new JSONArray();
        JSONArray outputs = new JSONArray();
        if(ojQuestionSample != null){
            inputs = new JSONArray(ojQuestionSample.getSampleInput());
            outputs = new JSONArray(ojQuestionSample.getSampleOutput());
        }

        List<TestSample> testSample = new ArrayList<>();
//        遍历组装
        for (int i = 0; i < inputs.length(); i++) {
            testSample.add(new TestSample(
                    id,
                    inputs.get(i).toString(),
                    outputs.get(i).toString(),
                    "",
                    false
            ));
        }

        return testSample;
    }

    //judge入口
    public TestResult doJudge(TestPack testPack) {
//        创建时间
        long time = System.currentTimeMillis();
        long qid = testPack.getQid();
        System.out.println(testPack);
//        拉取测试样例
        List<TestSample> testSamples = getTestSamples(qid);

        testPack.setTestSampleList(testSamples);
        OjQuestion question = questionMapper.selectById(qid);
//        时间内空间
        testPack.setMemoryLimit(question.getMemoryLimit());
        testPack.setTimeLimit(question.getTimeLimit() * 1000);
//          对测试对象进行设置
        testPack.setSubmitTime(time);
        testPack.setSubmitTimeFormat(
                new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss").format(time)
        );

//        测试结果
        TestResult testResult;
        try {
            //          新建判题机运行
            Judger judger = new Judger(testPack);
            testResult = judger.run();
        }finally {
            //        删除判题机（异步）
            containerUtils.deleteContainer(testPack.getContainerId());
        }

        return testResult;
    }
}
