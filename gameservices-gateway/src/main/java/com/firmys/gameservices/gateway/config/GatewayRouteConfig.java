package com.firmys.gameservices.gateway.config;

import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import com.firmys.gameservices.common.ServiceConstants;
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
                .route(ServiceConstants.INVENTORY + "service", r -> r
                        .path("/" + ServiceConstants.INVENTORY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.INVENTORY + "CircuitBreaker")
                                .setFallbackUri(inventoryHost + "/error")))
                        .uri(inventoryHost))
                .route(ServiceConstants.CHARACTER + "service", r -> r
                        .path("/" + ServiceConstants.CHARACTER + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.CHARACTER + "CircuitBreaker")
                                .setFallbackUri(characterHost + "/error")))
                        .uri(characterHost))
                .route(ServiceConstants.INVENTORIES + "service", r -> r
                        .path("/" + ServiceConstants.INVENTORIES + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.INVENTORIES + "CircuitBreaker")
                                .setFallbackUri(inventoriesHost + "/error")))
                        .uri(inventoriesHost))
                .route(ServiceConstants.CHARACTERS + "service", r -> r
                        .path("/" + ServiceConstants.CHARACTERS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.CHARACTERS + "CircuitBreaker")
                                .setFallbackUri(charactersHost + "/error")))
                        .uri(charactersHost))
                .route(ServiceConstants.ITEM + "service", r -> r
                        .path("/" + ServiceConstants.ITEM + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.ITEM + "CircuitBreaker")
                                .setFallbackUri(itemHost + "/error")))
                        .uri(itemHost))
                .route(ServiceConstants.CURRENCY + "service", r -> r
                        .path("/" + ServiceConstants.CURRENCY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.CURRENCY + "CircuitBreaker")
                                .setFallbackUri(currencyHost + "/error")))
                        .uri(currencyHost))
                .route(ServiceConstants.ITEMS + "service", r -> r
                        .path("/" + ServiceConstants.ITEMS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.ITEMS + "CircuitBreaker")
                                .setFallbackUri(itemsHost + "/error")))
                        .uri(itemsHost))
                .route(ServiceConstants.CURRENCIES + "service", r -> r
                        .path("/" + ServiceConstants.CURRENCIES + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceConstants.CURRENCIES + "CircuitBreaker")
                                .setFallbackUri(currenciesHost + "/error")))
                        .uri(currenciesHost))
                .build();
    }

}
