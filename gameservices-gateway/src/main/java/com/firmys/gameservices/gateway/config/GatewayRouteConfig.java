package com.firmys.gameservices.gateway.config;

import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import com.firmys.gameservices.common.ServiceStrings;
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
                .route(ServiceStrings.INVENTORY + "service", r -> r
                        .path("/" + ServiceStrings.INVENTORY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.INVENTORY + "CircuitBreaker")
                                .setFallbackUri(inventoryHost + "/error")))
                        .uri(inventoryHost))
                .route(ServiceStrings.CHARACTER + "service", r -> r
                        .path("/" + ServiceStrings.CHARACTER + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.CHARACTER + "CircuitBreaker")
                                .setFallbackUri(characterHost + "/error")))
                        .uri(characterHost))
                .route(ServiceStrings.INVENTORIES + "service", r -> r
                        .path("/" + ServiceStrings.INVENTORIES + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.INVENTORIES + "CircuitBreaker")
                                .setFallbackUri(inventoriesHost + "/error")))
                        .uri(inventoriesHost))
                .route(ServiceStrings.CHARACTERS + "service", r -> r
                        .path("/" + ServiceStrings.CHARACTERS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.CHARACTERS + "CircuitBreaker")
                                .setFallbackUri(charactersHost + "/error")))
                        .uri(charactersHost))
                .route(ServiceStrings.ITEM + "service", r -> r
                        .path("/" + ServiceStrings.ITEM + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.ITEM + "CircuitBreaker")
                                .setFallbackUri(itemHost + "/error")))
                        .uri(itemHost))
                .route(ServiceStrings.CURRENCY + "service", r -> r
                        .path("/" + ServiceStrings.CURRENCY + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.CURRENCY + "CircuitBreaker")
                                .setFallbackUri(currencyHost + "/error")))
                        .uri(currencyHost))
                .route(ServiceStrings.ITEMS + "service", r -> r
                        .path("/" + ServiceStrings.ITEMS + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.ITEMS + "CircuitBreaker")
                                .setFallbackUri(itemsHost + "/error")))
                        .uri(itemsHost))
                .route(ServiceStrings.CURRENCIES + "service", r -> r
                        .path("/" + ServiceStrings.CURRENCIES + "/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName(ServiceStrings.CURRENCIES + "CircuitBreaker")
                                .setFallbackUri(currenciesHost + "/error")))
                        .uri(currenciesHost))
                .build();
    }

}
