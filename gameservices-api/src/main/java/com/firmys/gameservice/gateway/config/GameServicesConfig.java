package com.firmys.gameservice.gateway.config;

import com.firmys.gameservice.common.security.SpringSecurityConfiguration;
import com.firmys.gameservice.common.ServicePaths;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpringSecurityConfiguration.class)
public class GameServicesConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(ServicePaths.INVENTORY + "service", r -> r
                        .path("/" + ServicePaths.INVENTORY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.INVENTORY + "CircuitBreaker")
                                .setFallbackUri("http://localhost:8080/error")))
                        .uri("http://localhost:9000"))
                .route(ServicePaths.CHARACTER + "service", r -> r
                        .path("/" + ServicePaths.CHARACTER + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.CHARACTER + "CircuitBreaker")
                                .setFallbackUri("http://localhost:8080/error")))
                        .uri("http://localhost:9001"))
                .build();
    }

}
