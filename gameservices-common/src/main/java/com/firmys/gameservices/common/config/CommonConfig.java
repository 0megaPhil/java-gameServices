package com.firmys.gameservices.common.config;

import static com.firmys.gameservices.common.CommonConstants.PROFILE_NOT_TEST;
import static com.firmys.gameservices.common.CommonConstants.PROFILE_SERVICE;

import com.firmys.gameservices.common.*;
import com.firmys.gameservices.common.error.GameDataExceptionController;
import com.firmys.gameservices.generated.models.CommonEntity;
import io.r2dbc.spi.ConnectionFactory;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Mono;

@Configuration
@Import({
  GameDataExceptionController.class,
  ConversionConfig.class,
  WebClientConfig.class,
  ServiceClient.class,
})
@EnableConfigurationProperties(CommonProperties.class)
public class CommonConfig {

  @Bean
  @Profile({PROFILE_SERVICE, PROFILE_NOT_TEST})
  public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    ResourceDatabasePopulator resource =
        new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
    initializer.setDatabasePopulator(resource);
    return initializer;
  }

  @Bean
  @Profile({PROFILE_SERVICE, PROFILE_NOT_TEST})
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
  @ConditionalOnMissingBean
  @Profile({PROFILE_SERVICE, PROFILE_NOT_TEST})
  public BeforeConvertCallback<CommonEntity> beforeConvertCallback() {
    return (d, table) -> {
      if (d.uuid() == null) {
        d =
            d.withUuid(UUID.randomUUID())
                .withCreated(Optional.ofNullable(d.created()).orElse(OffsetDateTime.now()))
                .withUpdated(OffsetDateTime.now());
      }
      return Mono.just(d);
    };
  }
}
