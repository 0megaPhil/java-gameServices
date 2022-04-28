package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.config.SdkConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = {SdkConfig.class})
public class InventorySdkIT {

    @Autowired
    InventorySdk sdk;
    @Autowired
    ItemSdk itemSdk;
    @Autowired
    CurrencySdk currencySdk;

    @Test
    public void create() {
        Mono<Inventory> mono = sdk.createInventory();
        AtomicReference<Inventory> inventory = new AtomicReference<>();
        mono.map(i -> {
            Assertions.assertThat(i).isNotNull();
            Assertions.assertThat(i.getUuid()).isNotNull();
            Assertions.assertThat(i.getOwnedCurrencies()).isNotNull();
            Assertions.assertThat(i.getOwnedItems()).isNotNull();
            inventory.set(i);
            return i;
        }).then().block();
        sdk.deleteInventory(inventory.get().getUuid()).then().block();
    }

    // TODO - Add assertions for OwnedItems and OwnedCurrencies
    @Test
    public void addAndConsumeOwnedItems() {

    }

    @Test
    void creditAndDebitCurrency() {

    }

}
