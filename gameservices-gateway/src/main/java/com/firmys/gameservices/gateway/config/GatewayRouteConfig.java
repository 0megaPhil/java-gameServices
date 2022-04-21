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

    @Value("${spring.cloud.gateway.routes[2].uri}")
    private String inventoriesHost;

    @Value("${spring.cloud.gateway.routes[3].uri}")
    private String charactersHost;

    @Value("${spring.cloud.gateway.routes[4].uri}")
    private String itemHost;

    @Value("${spring.cloud.gateway.routes[5].uri}")
    private String currencyHost;

    @Value("${spring.cloud.gateway.routes[6].uri}")
    private String itemsHost;

    @Value("${spring.cloud.gateway.routes[7].uri}")
    private String currenciesHost;

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
                                .setFallbackUri(inventoriesHost + "/error")))
                        .uri(inventoriesHost))
                .route(ServicePaths.CHARACTERS + "service", r -> r
                        .path("/" + ServicePaths.CHARACTERS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.CHARACTERS + "CircuitBreaker")
                                .setFallbackUri(charactersHost + "/error")))
                        .uri(charactersHost))
                .route(ServicePaths.ITEM + "service", r -> r
                        .path("/" + ServicePaths.ITEM + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.ITEM + "CircuitBreaker")
                                .setFallbackUri(itemHost + "/error")))
                        .uri(itemHost))
                .route(ServicePaths.CURRENCY + "service", r -> r
                        .path("/" + ServicePaths.CURRENCY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.CURRENCY + "CircuitBreaker")
                                .setFallbackUri(currencyHost + "/error")))
                        .uri(currencyHost))
                .route(ServicePaths.ITEMS + "service", r -> r
                        .path("/" + ServicePaths.ITEMS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.ITEMS + "CircuitBreaker")
                                .setFallbackUri(itemsHost + "/error")))
                        .uri(itemsHost))
                .route(ServicePaths.CURRENCIES + "service", r -> r
                        .path("/" + ServicePaths.CURRENCIES + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServicePaths.CURRENCIES + "CircuitBreaker")
                                .setFallbackUri(currenciesHost + "/error")))
                        .uri(currenciesHost))
                .build();
    }

}
