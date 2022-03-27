package com.firmys.gameservice.characters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.UUID;

@SpringBootApplication
@EnableCaching
public class CharacterApp {

    public static void main(String[] args) {
        SpringApplication.run(CharacterApp.class, args);
    }
}
