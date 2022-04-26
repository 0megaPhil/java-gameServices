package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.models.OwnedItem;
import com.firmys.gameservices.sdk.config.SdkConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@SpringBootTest(classes = {SdkConfig.class})
public class InventorySdkIT {

    @Autowired
    InventorySdk sdk;
    @Autowired
    ItemSdk itemSdk;

    @Test
    public void addOwnedItem() {
        Inventory inventory = sdk.addInventory().block();
        System.out.println("Before OwnedItem: " + inventory);

        Item generatedItem = InventoryTestUtilities.generateItem();
        Item createdItem = itemSdk.addItem(generatedItem).block();

        inventory = sdk.addOwnedItemInventory(Objects.requireNonNull(inventory).getUuid().toString(),
                1, createdItem).block();

        System.out.println("With OwnedItem: " + inventory);

    }

    @Test
    public void addOwnedItems() {
        Inventory inventory = sdk.addInventory().block();
        System.out.println("Before OwnedItem: " + inventory);

        Item generatedItemOne = InventoryTestUtilities.generateItem();
        Item itemOne = itemSdk.addItem(generatedItemOne).block();
        Item generatedItemTwo = InventoryTestUtilities.generateItem();
        Item itemTwo = itemSdk.addItem(generatedItemTwo).block();

        inventory = sdk.addOwnedItemsInventory("eafcb0cd-ee55-44d4-ba51-a4dfb895fb4a",
                1, List.of(Objects.requireNonNull(itemOne), Objects.requireNonNull(itemTwo))).block();

        System.out.println("With OwnedItem: " + inventory);

    }

}
