package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.Set;

@SpringBootTest
@ActiveProfiles("inventories-sdk-test")
class InventoriesSdkTest {

    @MockBean
    GatewayClient gatewayClient;

    @MockBean
    InventoriesSdk inventoriesSdk;

    @Test
    void addMultipleInventory() {
//        Mockito.when(inventoriesSdk.addMultipleInventory(1)).thenReturn(Mono.defer(Set.of()))
//        inventoriesSdk.addMultipleInventory(0);
    }

    @Test
    void deleteMultipleInventory() {
    }

    @Test
    void findMultipleInventory() {
    }

    @Test
    void updateMultipleInventory() {
    }
}