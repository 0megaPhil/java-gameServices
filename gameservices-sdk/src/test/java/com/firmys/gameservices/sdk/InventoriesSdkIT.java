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
public class InventoriesSdkIT {

    @Autowired
    InventoriesSdk inventoriesSdk;

    @Test
    public void checkInventoriesSdk() {
        System.out.println("START COUNT: " + inventoriesSdk.findMultipleInventory(new HashSet<>()).block().size());
//        Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(2).block();

        Set<Inventory> inventories = inventoriesSdk.findMultipleInventory(new HashSet<>()).block();
//        System.out.println(inventories.toString());
//        System.out.println("NEW COUNT: " + inventories.size());

        inventoriesSdk.deleteMultipleInventory(new HashSet<>(), inventories).block();
//        inventories = inventoriesSdk.findMultipleInventory(new HashSet<>()).block();
//        System.out.println("REMAINING: " + inventories.toString());
//        System.out.println("REMAINING COUNT: " + inventories.size());
//
        inventories.forEach(System.out::println);
    }
}
