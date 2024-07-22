package com.firmys.gameservice.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(ConfigApplication.class);
    application.run(args);
  }
}
