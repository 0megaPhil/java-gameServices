package com.firmys.gameservice.gateway;

import com.firmys.gameservice.common.config.GameServiceCommonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@Import(GameServiceCommonConfig.class)
@OpenAPIDefinition(info = @Info(title = "GameServicesGateway", version = "0.1.0", description = "Documentation APIs v0.1.0"))
public class GameServicesGateway {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GameServicesGateway.class);
        application.run(args);
    }
}
