package com.firmys.gameservices.sdk;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.client.GatewayClient;
import com.firmys.gameservices.sdk.controllers.InventoriesSdk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = {InventoriesSdk.class, GatewayClient.class})
public class SdkIT {

    @Autowired
    InventoriesSdk inventoriesSdk;

    @Test
    public void checkInventoriesSdk() {
//        Inventory first = new Inventory();
//        Set<Inventory> toAdd = Set.of(new Inventory());
//        Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(toAdd).block();

        Set<Inventory> inventories = inventoriesSdk.findMultipleInventory(new HashSet<>()).block();
        System.out.println(inventories.toString());
    }
}
