package com.firmys.gameservices.webui.config;

import com.firmys.gameservices.sdk.client.GatewayClient;
import com.firmys.gameservices.sdk.config.SdkConfig;
import com.firmys.gameservices.sdk.services.InventoriesSdk;
import com.firmys.gameservices.sdk.services.InventorySdk;
import com.firmys.gameservices.sdk.services.ItemSdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SdkConfig.class, GatewayClient.class})
public class WebSdkConfig {

    @Bean
    InventoriesSdk inventoriesSdk(@Autowired GatewayClient client) {
        return new InventoriesSdk(client);
    }

    @Bean
    InventorySdk inventorySdk(@Autowired GatewayClient client) {
        return new InventorySdk(client);
    }

    @Bean
    ItemSdk itemSdk(@Autowired GatewayClient client) {
        return new ItemSdk(client);
    }
}
