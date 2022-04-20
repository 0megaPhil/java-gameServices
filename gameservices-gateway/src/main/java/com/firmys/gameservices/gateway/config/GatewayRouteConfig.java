package com.firmys.gameservices.gateway.config;

import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import com.firmys.gameservices.common.ServicePaths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpringSecurityConfiguration.class)
public class GatewayRouteConfig {

    @Value("${spring.cloud.gateway.routes[0].uri}")
    private String inventoryHost;

    @Value("${spring.cloud.gateway.routes[1].uri}")
    private String characterHost;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(ServicePaths.INVENTORY + "service", r -> r
                        .path("/" + ServicePaths.INVENTORY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.INVENTORY + "CircuitBreaker")
                                .setFallbackUri(inventoryHost + "/error")))
                        .uri(inventoryHost))
                .route(ServicePaths.CHARACTER + "service", r -> r
                        .path("/" + ServicePaths.CHARACTER + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.CHARACTER + "CircuitBreaker")
                                .setFallbackUri(characterHost + "/error")))
                        .uri(characterHost))
                .route(ServicePaths.INVENTORIES + "service", r -> r
                        .path("/" + ServicePaths.INVENTORIES + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.INVENTORIES + "CircuitBreaker")
                                .setFallbackUri(inventoryHost + "/error")))
                        .uri(inventoryHost))
                .route(ServicePaths.CHARACTERS + "service", r -> r
                        .path("/" + ServicePaths.CHARACTERS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.CHARACTERS + "CircuitBreaker")
                                .setFallbackUri(characterHost + "/error")))
                        .uri(characterHost))
                .build();
    }

}
