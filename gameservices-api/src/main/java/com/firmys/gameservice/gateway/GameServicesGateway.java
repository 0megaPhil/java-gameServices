package com.firmys.gameservice.gateway;

import com.firmys.gameservice.common.config.GameServiceCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@Import(GameServiceCommonConfig.class)
public class GameServicesGateway {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GameServicesGateway.class);
        application.run(args);
    }
}
