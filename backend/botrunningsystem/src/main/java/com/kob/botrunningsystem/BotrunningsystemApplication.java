package com.kob.botrunningsystem;

import com.kob.botrunningsystem.service.impl.BotServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotrunningsystemApplication {

    public static void main(String[] args) {
        BotServiceImpl.botPool.start();
        SpringApplication.run(BotrunningsystemApplication.class, args);
    }

}
