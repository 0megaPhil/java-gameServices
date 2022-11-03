package com.firmys.gameservices.characters;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.config.GameServiceCommonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GameServiceCommonConfig.class)
@OpenAPIDefinition(info = @Info(title = ServiceConstants.CHARACTER_SERVICE, version = ServiceConstants.VERSION, description = "Game Services API"))
public class CharacterServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CharacterServiceApp.class, args);
    }
}
