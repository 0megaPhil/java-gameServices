package com.firmys.gameservices.sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
@EnableWebFlux
public class GameServicesSdk {

    public static void main(String[] args) {
        SpringApplication.run(GameServicesSdk.class, args);
    }
}
