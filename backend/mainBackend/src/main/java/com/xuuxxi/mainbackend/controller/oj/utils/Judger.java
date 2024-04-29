package com.xuuxxi.mainbackend.controller.oj.utils;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
public class Judger extends JudgeCore{

    public Judger(TestPack testPack) {
//        System.out.println(testPack);
        this.testPack = testPack;
    }

    /**
     * 完整的判题机运行过程
     */
    public TestResult run(){

        //  初始化
        this.init();
        //  编译
        String compile = this.compile();
//        System.out.println("编译 :" + compile);
        // 编译结果需要为空或null才是编译成功，可以继续执行代码
        if("".equals(compile) || compile == null || " ".equals(compile)){
            String execute = this.execute();
//            System.out.println("运行 :" + execute);
            checkAnswer();
        }else{
            testResult.setPass(false);
            testResult.setTitle("Compile Error");
            testResult.setMessage(compile);
        }

        return testResult;
    }
}
