package com.firmys.gameservices.service.config;

import static com.firmys.gameservices.common.CommonConstants.PROFILE_NOT_TEST;
import static com.firmys.gameservices.common.CommonConstants.PROFILE_SERVICE;
import static java.time.ZoneOffset.UTC;

import com.firmys.gameservices.common.config.CommonConfig;
import com.firmys.gameservices.common.config.WebConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.service.GameServiceClient;
import com.firmys.gameservices.service.error.ServiceExceptionController;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

@Configuration
@Import({
  SpringSecurityConfiguration.class,
  ServiceExceptionController.class,
  ConversionConfig.class,
  GameServiceClient.class,
  CommonConfig.class,
  WebConfig.class
})
public class ServiceConfig {

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
