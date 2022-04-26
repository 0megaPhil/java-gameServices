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
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = {SdkConfig.class})
public class InventorySdkIT {

    @Autowired
    InventorySdk sdk;
    @Autowired
    ItemSdk itemSdk;
    @Autowired
    CurrencySdk currencySdk;


    @Test
    void addAndDeleteInventory() {
        Inventory inventory = sdk.addInventory().block();
        Assertions.assertThat(inventory).isNotNull();
        sdk.deleteInventory(null, Objects.requireNonNull(inventory)).block();
        try {
            sdk.findByUuidPathInventory(inventory.getUuid().toString()).block();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAndConsumeOwnedItems() {
        Inventory inventory = sdk.addInventory().block();
        System.out.println("Before OwnedItem: " + inventory);

        Item generatedItemOne = InventoryTestUtilities.generateItem();
        Item itemOne = itemSdk.addItem(generatedItemOne).block();
        Item generatedItemTwo = InventoryTestUtilities.generateItem();
        Item itemTwo = itemSdk.addItem(generatedItemTwo).block();

        inventory = sdk.addOwnedItemsInventory(Objects.requireNonNull(inventory).getUuid().toString(),
                1, List.of(Objects.requireNonNull(itemOne), Objects.requireNonNull(itemTwo))).block();

        System.out.println("Add OwnedItem: " + inventory);

        inventory = sdk.consumeOwnedItemsInventory(Objects.requireNonNull(inventory).getUuid().toString(), 1,
                List.of(Objects.requireNonNull(itemOne), Objects.requireNonNull(itemTwo))).block();

        System.out.println("Consume OwnedItem: " + inventory);

        sdk.deleteByUuidInventory(Objects.requireNonNull(inventory).getUuid().toString()).block();
    }

    @Test
    void creditAndDebitCurrency() {
        Inventory inventory = sdk.addInventory().filter(Objects::nonNull).block();
        System.out.println("Before OwnedItem: " + inventory);

        Currency generatedCurrency = InventoryTestUtilities.generateCurrency();
        Currency currencyOne = currencySdk.addCurrency(generatedCurrency).filter(Objects::nonNull).block();

        inventory = sdk.creditCurrencyByUuidInventory(Objects.requireNonNull(inventory).getUuid().toString(),
                10, Objects.requireNonNull(currencyOne)).block();

        System.out.println("Credit OwnedCurrency: " + inventory);

        inventory = sdk.debitCurrencyByUuidInventory(Objects.requireNonNull(inventory).getUuid().toString(),
                10, Objects.requireNonNull(currencyOne)).block();

        System.out.println("Debit OwnedCurrency: " + inventory);

        sdk.deleteByUuidInventory(Objects.requireNonNull(inventory).getUuid().toString()).block();

    }

}
