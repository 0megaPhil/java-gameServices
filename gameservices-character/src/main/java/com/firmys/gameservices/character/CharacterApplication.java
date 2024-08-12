package com.firmys.gameservices.character;

import com.firmys.gameservices.common.CommonConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(
    info =
        @Info(
            title = CommonConstants.CHARACTER_SERVICE,
            version = CommonConstants.VERSION,
            description = "Game Services API"))
public class CharacterApplication {

  public static void main(String[] args) {
    SpringApplication.run(CharacterApplication.class, args);
  }
}
