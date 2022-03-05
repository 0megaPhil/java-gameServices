package com.firmys.gameservice.api.config;

import com.firmys.gameservice.api.GameServiceProperties;
import com.firmys.gameservice.api.impl.ServiceClient;
import com.firmys.gameservice.inventory.Inventory;
import com.firmys.gameservices.world.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(GameServiceProperties.class)
public class GameServicesConfig {
//
//    @Value("${gameservice.service.inventory.port}")
//    String inventoryPort;
//    @Value("${gameservice.service.characters.port}")
//    String charactersPort;
//    @Value("${gameservice.service.world.port}")
//    String worldPort;
//
//    @Value("${gameservice.service.inventory.host}")
//    String inventoryHost = "localhost";

    @Autowired
    ConfigurableApplicationContext context;

    @Bean
    public boolean startServices(GameServiceProperties props) {
        Inventory.main(
                new String[]{"--server.port=" + props.getServiceMap().get(ServiceClient.INVENTORY).getPort()});
        World.main(
                new String[]{"--server.port=" + props.getServiceMap().get(ServiceClient.WORLD).getPort()});
        return true;
    }

//    @Bean
//    public InventoryClient inventoryClient(RestTemplate inventoryRestTemplate) {
//        return new InventoryClient(inventoryRestTemplate);
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .build();
    }

}
