package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Currency;
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
    @Autowired
    CurrencySdk currencySdk;

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

        Currency generatedCurrency = InventoryTestUtilities.generateCurrency();
        Currency currencyOne = currencySdk.addCurrency(generatedCurrency).block();

        inventory = sdk.addOwnedItemsInventory(inventory.getUuid().toString(),
                1, List.of(Objects.requireNonNull(itemOne), Objects.requireNonNull(itemTwo))).block();

        System.out.println("Add OwnedItem: " + inventory);

        inventory = sdk.consumeOwnedItemsInventory(inventory.getUuid().toString(), 1,
                List.of(Objects.requireNonNull(itemOne), Objects.requireNonNull(itemTwo))).block();

        System.out.println("Consume OwnedItem: " + inventory);

        inventory = sdk.creditCurrencyByUuidInventory(inventory.getUuid().toString(),
                10, currencyOne).block();

        System.out.println("Add OwnedCurrency: " + inventory);

        inventory = sdk.debitCurrencyByUuidInventory(inventory.getUuid().toString(),
                10, currencyOne).block();

        System.out.println("Consume OwnedCurrency: " + inventory);

        sdk.deleteByUuidInventory(inventory.getUuid().toString()).block();

//        Inventory notExist = sdk.findByUuidPathInventory(inventory.getUuid().toString()).block();

    }

}
