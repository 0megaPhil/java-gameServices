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

@SpringBootTest(classes = {InventoriesSdk.class, GatewayClient.class})
public class InventoriesSdkIT {

    @Autowired
    InventoriesSdk inventoriesSdk;

    @Test
    public void createAndDeleteInventories() {
        Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(2).block();

        Assertions.assertThat(addedSet).isNotNull();
        Assertions.assertThat(addedSet.size()).isEqualTo(2);
        Inventory inventory = addedSet.iterator().next();
        addedSet.forEach(i -> {
            Assertions.assertThat(i.getUuid()).isNotNull();
            Assertions.assertThat(i.getOwnedCurrencies()).isNotNull();
            Assertions.assertThat(i.getOwnedCurrencies().getOwnedCurrencyMap()).isNotNull();
            Assertions.assertThat(i.getOwnedItems()).isNotNull();
            Assertions.assertThat(i.getOwnedItems().getOwnedItemMap()).isNotNull();
        });

        inventoriesSdk.deleteMultipleInventory(new HashSet<>(), addedSet).block();

        Set<Inventory> foundSet = inventoriesSdk.findMultipleInventory(null).block();
        Assertions.assertThat(foundSet).doesNotContain(addedSet.toArray(new Inventory[]{}));
    }

    @Test
    public void createInventoriesAddItems() {
        Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(2).block();
        Set<Item> itemSet = Set.of(InventoryTestUtilities.generateItem(), InventoryTestUtilities.generateItem());

    }
}
