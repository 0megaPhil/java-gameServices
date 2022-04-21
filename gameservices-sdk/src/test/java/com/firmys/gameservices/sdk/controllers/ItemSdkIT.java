package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = {ItemSdk.class, GatewayClient.class})
public class ItemSdkIT {

    @Autowired
    ItemSdk sdk;

    @Test
    public void create() {
       Item item = sdk.addItem(InventoryTestUtilities.generateItem()).block();
       System.out.println(item);
    }

}
