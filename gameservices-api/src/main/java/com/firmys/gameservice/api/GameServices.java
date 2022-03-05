package com.firmys.gameservice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameServices {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GameServices.class);
        application.run(args);
    }
}
