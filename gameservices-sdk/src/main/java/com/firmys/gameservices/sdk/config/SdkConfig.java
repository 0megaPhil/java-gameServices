package com.firmys.gameservices.sdk.config;

import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import com.firmys.gameservices.sdk.services.InventoriesSdk;
import com.firmys.gameservices.sdk.services.InventorySdk;
import com.firmys.gameservices.sdk.services.ItemSdk;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GatewayDetails.class)
public class SdkConfig {

    @Bean
    public InventoriesSdk inventoriesSdk(GatewayDetails gatewayDetails) {
        return new InventoriesSdk(gatewayDetails);
    }

    @Bean
    public InventorySdk inventorySdk(GatewayDetails gatewayDetails) {
        return new InventorySdk(gatewayDetails);
    }

    @Bean
    public ItemSdk itemSdk(GatewayDetails gatewayDetails) {
        return new ItemSdk(gatewayDetails);
    }

}
