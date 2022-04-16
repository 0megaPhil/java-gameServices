package com.firmys.gameservice.characters;

import com.firmys.gameservice.common.config.GameServiceCommonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GameServiceCommonConfig.class)
@OpenAPIDefinition(info = @Info(title = "CharacterService", version = "0.1.0", description = "Documentation APIs v0.1.0"))
public class CharacterServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CharacterServiceApp.class, args);
    }
}
