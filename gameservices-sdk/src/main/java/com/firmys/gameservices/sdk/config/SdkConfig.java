package com.firmys.gameservices.sdk.config;

import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import com.firmys.gameservices.sdk.services.*;
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

    @Bean
    public ItemsSdk itemsSdk(GatewayDetails gatewayDetails) {
        return new ItemsSdk(gatewayDetails);
    }

    @Bean
    public CurrencySdk currencySdk(GatewayDetails gatewayDetails) {
        return new CurrencySdk(gatewayDetails);
    }

    @Bean
    public CurrenciesSdk currenciesSdk(GatewayDetails gatewayDetails) {
        return new CurrenciesSdk(gatewayDetails);
    }

    @Bean
    public CharacterSdk characterSdk(GatewayDetails gatewayDetails) {
        return new CharacterSdk(gatewayDetails);
    }

    @Bean
    public CharactersSdk charactersSdk(GatewayDetails gatewayDetails) {
        return new CharactersSdk(gatewayDetails);
    }

}
