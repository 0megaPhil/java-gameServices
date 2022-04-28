package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.config.SdkConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@SpringBootTest(classes = {SdkConfig.class})
public class InventoriesSdkIT {

    @Autowired
    InventoriesSdk inventoriesSdk;

    @Test
    public void createAndDeleteInventories() {
        Mono<Set<Inventory>> addedSet = inventoriesSdk.createSetInventory(5);
        AtomicReference<Set<Inventory>> invSet = new AtomicReference<>(new HashSet<>());
        addedSet.map(set -> {
            Assertions.assertThat(set).isNotNull();
            Assertions.assertThat(set.size()).isEqualTo(5);
            set.forEach(inventory -> {
                Assertions.assertThat(inventory.getUuid()).isNotNull();
                Assertions.assertThat(inventory.getOwnedCurrencies()).isNotNull();
                Assertions.assertThat(inventory.getOwnedCurrencies().getOwnedCurrencyMap()).isNotNull();
                Assertions.assertThat(inventory.getOwnedItems()).isNotNull();
                Assertions.assertThat(inventory.getOwnedItems().getOwnedItemMap()).isNotNull();
                invSet.get().add(inventory);
            });
            return set;
        }).subscribeOn(Schedulers.parallel()).then().block();

        inventoriesSdk.deleteSetInventory(invSet.get()
                .stream().map(Inventory::getUuid).collect(Collectors.toSet()))
                .then().block();
    }

}
