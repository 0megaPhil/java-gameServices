package com.firmys.gameservices.common.config;

import com.firmys.gameservices.common.error.GameDataExceptionController;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GameDataExceptionController.class})
public class CommonConfig {

  @Value("${server.ssl.key-password}")
  private String keyStorePassword;

  @Bean
  public ServerProperties serverProperties() {
    Ssl ssl = new Ssl();
    ssl.setKeyStorePassword(keyStorePassword);
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setSsl(ssl);
    return serverProperties;
  }

  @Bean
  public OpenApiCustomiser openApiCustomiser() {
    return openApi ->
        openApi.getPaths().values().stream()
            .flatMap(pathItem -> pathItem.readOperations().stream())
            .forEach(
                operation -> {
                  String controllerName =
                      operation.getTags().stream()
                          .filter(s -> s.contains("controller"))
                          .findFirst()
                          .orElse("unknown-controller")
                          .split("-")[0];
                  String capitalized =
                      Character.toUpperCase(controllerName.charAt(0)) + controllerName.substring(1);
                  if (operation.getOperationId().contains("_")) {
                    operation.setOperationId(
                        operation.getOperationId().split("_")[0] + capitalized);
                  } else {
                    operation.setOperationId(operation.getOperationId() + capitalized);
                  }
                });
  }
}
