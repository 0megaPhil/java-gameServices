package com.firmys.gameservices.common.config;

import static com.firmys.gameservices.common.CommonConstants.PROFILE_NOT_TEST;
import static com.firmys.gameservices.common.CommonConstants.PROFILE_SERVICE;
import static java.time.ZoneOffset.UTC;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.ServiceClient;
import com.firmys.gameservices.common.error.ServiceExceptionController;
import com.firmys.gameservices.generated.models.CommonEntity;
import io.r2dbc.spi.ConnectionFactory;
import java.time.ZonedDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@Import({
  ServiceExceptionController.class,
  ConversionConfig.class,
  WebClientConfig.class,
  ServiceClient.class,
  DataConfig.class
})
@EnableConfigurationProperties(CommonProperties.class)
public class CommonConfig {

  //  @Bean
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
  public org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback<CommonEntity>
      beforeConvertCallbackR2dbc() {
    return (d, table) -> {
      if (d.id() == null) {
        d =
            d.withId(ObjectId.get())
                .withCreated(
                    Optional.ofNullable(d.created())
                        .orElse(ZonedDateTime.now(UTC).toOffsetDateTime()))
                .withUpdated(ZonedDateTime.now(UTC).toOffsetDateTime());
      }
      return Mono.just(d);
    };
  }

  @Bean
  @ConditionalOnMissingBean
  @Profile({PROFILE_SERVICE, PROFILE_NOT_TEST})
  public BeforeConvertCallback<CommonEntity> beforeConvertCallback() {
    return (d, table) -> {
      if (d.id() == null) {
        d =
            d.withId(ObjectId.get())
                .withCreated(
                    Optional.ofNullable(d.created())
                        .orElse(ZonedDateTime.now(UTC).toOffsetDateTime()))
                .withUpdated(ZonedDateTime.now(UTC).toOffsetDateTime());
      }
      return d;
    };
  }
}
