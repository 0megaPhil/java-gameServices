package com.firmys.gameservice.api.config;

import com.firmys.gameservice.api.GameServiceProperties;
import com.firmys.gameservice.api.impl.ServiceClient;
import com.firmys.gameservice.inventory.InventoryApp;
import com.firmys.gameservices.world.World;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(GameServiceProperties.class)
public class GameServicesConfig {

    @Bean
    public boolean startServices(GameServiceProperties props) {
        InventoryApp.main(
                new String[]{"--server.port=" + props.getServiceMap().get(ServiceClient.INVENTORY).getPort()});
        World.main(
                new String[]{"--server.port=" + props.getServiceMap().get(ServiceClient.WORLD).getPort()});
        return true;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .build();
    }

}
