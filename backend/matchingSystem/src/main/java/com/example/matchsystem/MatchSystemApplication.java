package com.example.matchsystem;

import com.example.matchsystem.service.impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchSystemApplication {

    public static void main(String[] args) {
        MatchingServiceImpl.m.start();
        SpringApplication.run(MatchSystemApplication.class, args);
    }

}
