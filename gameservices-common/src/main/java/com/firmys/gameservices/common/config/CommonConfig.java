package com.firmys.gameservices.common.config;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.common.error.GameDataExceptionController;
import io.r2dbc.spi.ConnectionFactory;
import java.util.UUID;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Mono;

@Configuration
@Import({GameDataExceptionController.class, WebClientConfig.class, GatewayClient.class})
@EnableConfigurationProperties(CommonProperties.class)
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
  public ConnectionFactoryInitializer initializer(
      @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    ResourceDatabasePopulator resource =
        new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
    initializer.setDatabasePopulator(resource);
    return initializer;
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

  @Bean
  public BeforeConvertCallback<CommonEntity> beforeConvertCallback() {
    return (d, table) -> {
      if (d.uuid() == null) {
        d = d.withUuid(UUID.randomUUID());
      }
      return Mono.just(d);
    };
  }
}
