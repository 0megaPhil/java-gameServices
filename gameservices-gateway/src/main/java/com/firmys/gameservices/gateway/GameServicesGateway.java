package com.firmys.gameservices.gateway;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.config.GameServiceCommonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import(GameServiceCommonConfig.class)
@OpenAPIDefinition(info = @Info(title = ServiceConstants.GAME_SERVICES_GATEWAY, version = ServiceConstants.VERSION, description = "Game Services API"))
public class GameServicesGateway {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GameServicesGateway.class);
        application.run(args);
    }
}
