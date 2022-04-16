package com.firmys.gameservice.inventory.config;

import com.firmys.gameservice.inventory.impl.LocationInventory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryConfig {

    @Bean
    LocationInventory locationInventory() {
        return new LocationInventory();
    }
}
