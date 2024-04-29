package com.xuuxxi.mainbackend.controller.oj.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Xuuxxi
 * @Date: 2024/4/24
 */
@Service
public class ContainerUtils {
    /**
     * 删除容器
     * 方法异步的，这里不用用户等响应，直接返回评测结果
     * 可以使用docker 定时任务，定时删除容器
     */
    @Async
    public void deleteContainer(String id){
//        System.out.println(id);
//        System.out.println(Commands.STOP.replace("%id",id));
        String replace = Commands.STOP.replace("%id", id);
//        System.out.println(replace);
        String[] split = replace.split(",");
//        System.out.println(split.length);
        doCommand(Arrays.asList(split));
        doCommand(Arrays.asList(Commands.DELETE.replace("%id",id).split(",")));
    }

    public String doCommand(List<String> commands){
        StringBuilder result = new StringBuilder();

        ProcessBuilder processBuilder = new ProcessBuilder(commands);

        processBuilder.redirectErrorStream(true);
        Process process;

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while((line = br.readLine()) != null){
                result.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(result);

        return result.toString();
    }

}