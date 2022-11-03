package com.firmys.gameservices.webui.config;

import com.firmys.gameservices.sdk.gateway.GatewayClient;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import com.firmys.gameservices.sdk.services.InventoriesSdk;
//import com.firmys.gameservices.sdk.services.InventorySdk;
//import com.firmys.gameservices.sdk.services.ItemSdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GatewayDetails.class, GatewayClient.class})
public class WebSdkConfig {

    @Bean
    InventoriesSdk inventoriesSdk(@Autowired InventoriesSdk inventoriesSdk) {
        return inventoriesSdk;
    }

//    @Bean
//    InventorySdk inventorySdk(@Autowired GatewayClient client) {
//        return new InventorySdk(client);
//    }
//
//    @Bean
//    ItemSdk itemSdk(@Autowired GatewayClient client) {
//        return new ItemSdk(client);
//    }
}
