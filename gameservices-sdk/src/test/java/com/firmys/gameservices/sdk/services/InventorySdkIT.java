package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.config.SdkConfig;
import com.firmys.gameservices.sdk.services.utilities.EntityGenerators;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = {SdkConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InventorySdkIT {

    @Autowired
    CharacterSdk sdk;

    @Autowired
    InventorySdk inventorySdk;

    @Autowired
    ItemSdk itemSdk;

    @Autowired
    CurrencySdk currencySdk;

    AtomicReference<Character> characterRef = new AtomicReference<>();
    AtomicReference<Inventory> inventoryRef = new AtomicReference<>();
    AtomicReference<Item> itemRef = new AtomicReference<>();
    AtomicReference<Currency> currencyRef = new AtomicReference<>();

    @BeforeAll
    void generate() {
        inventorySdk.createInventory().map(i -> {
            inventoryRef.set(i);
            return i;
        }).then().block();

        itemSdk.createItem(EntityGenerators.generateItem()).map(i -> {
            itemRef.set(i);
            return i;
        }).then().block();

        currencySdk.createCurrency(EntityGenerators.generateCurrency()).map(i -> {
            currencyRef.set(i);
            return i;
        }).then().block();
    }

    @Test
    void addOwnedItemAddOwnedCurrency() {

        // Add OwnedItem
        inventorySdk.addConsumableItemInventory(inventoryRef.get().getUuid(),
                        itemRef.get().getUuid(), new Random().nextInt(1, 9))
                .then().block();

        // Add OwnedCurrency
        inventorySdk.creditTransactionalCurrencyInventory(inventoryRef.get().getUuid(),
                currencyRef.get().getUuid(), new Random().nextInt(1, 255)).then().block();
    }

}
