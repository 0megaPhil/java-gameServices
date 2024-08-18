package com.firmys.gameservices.world;

import com.firmys.gameservices.common.CommonConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = CommonConstants.WORLD_SERVICE,
            version = CommonConstants.VERSION,
            description = "Game Services API"))
public class WorldApplication {

  public static void main(String[] args) {
    SpringApplication.run(WorldApplication.class, args);
  }
}
