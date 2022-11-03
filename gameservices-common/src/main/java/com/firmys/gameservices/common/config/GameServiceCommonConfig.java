package com.firmys.gameservices.common.config;

import com.firmys.gameservices.common.converters.MatchStrategyConverter;
import com.firmys.gameservices.common.error.GameDataExceptionController;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Duration;

@Configuration
@Import({
        SpringSecurityConfiguration.class,
        GameDataExceptionController.class
})
public class GameServiceCommonConfig {
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(200)).build())
                .build());
    }

    @Bean
    OpenApiCustomiser openApiCustomiser() {
        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    String controllerName = operation.getTags().stream().filter(s -> s.contains("controller"))
                            .findFirst().orElse("unknown-controller").split("-")[0];
                    String capitalized = Character.toUpperCase(controllerName.charAt(0)) + controllerName.substring(1);
                    if(operation.getOperationId().contains("_")) {
                        operation.setOperationId(operation.getOperationId().split("_")[0] + capitalized);
                    } else {
                        operation.setOperationId(operation.getOperationId() + capitalized);
                    }
                });
    }

    @Bean
    MatchStrategyConverter matchStrategyConverter() {
        return new MatchStrategyConverter();
    }
}
