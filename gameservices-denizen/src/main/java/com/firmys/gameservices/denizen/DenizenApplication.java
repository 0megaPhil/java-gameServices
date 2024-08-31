package com.firmys.gameservices.denizen;

import static com.firmys.gameservices.common.CommonConstants.DENIZEN_SERVICE;
import static com.firmys.gameservices.common.CommonConstants.VERSION;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = DENIZEN_SERVICE, version = VERSION, description = DENIZEN_SERVICE))
public class DenizenApplication {

  public static void main(String[] args) {
    SpringApplication.run(DenizenApplication.class, args);
  }
}
