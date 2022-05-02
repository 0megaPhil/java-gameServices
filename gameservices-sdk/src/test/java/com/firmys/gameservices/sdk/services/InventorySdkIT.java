package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.config.SdkConfig;
import com.firmys.gameservices.sdk.services.utilities.EntityGenerators;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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
public class InventorySdkIT {

    @Autowired
    InventorySdk inventorySdk;
    @Autowired
    InventoriesSdk inventoriesSdk;
    @Autowired
    ItemsSdk itemsSdk;
    @Autowired
    ItemSdk itemSdk;
    @Autowired
    CurrencySdk currencySdk;

    @Test
    public void create() {
        Mono<Inventory> mono = inventorySdk.createInventory();
        mono.map(in -> {
            System.out.println("GENERATED: " + in);
            Assertions.assertThat(in).isNotNull();
            Assertions.assertThat(in.getUuid()).isNotNull();
            Assertions.assertThat(in.getOwnedCurrencies()).isNotNull();
            Assertions.assertThat(in.getOwnedItems()).isNotNull();
            return in;
        }).then().block();
    }

    @Test
    public void createSet() {
        Mono<Set<Inventory>> addedSet = inventoriesSdk.createSetInventory(5);
        addedSet.map(set -> {
            Assertions.assertThat(set).isNotNull();
            Assertions.assertThat(set.size()).isEqualTo(5);
            set.forEach(inventory -> {
                System.out.println("GENERATED: " + inventory);
                Assertions.assertThat(inventory.getUuid()).isNotNull();
                Assertions.assertThat(inventory.getOwnedCurrencies()).isNotNull();
                Assertions.assertThat(inventory.getOwnedCurrencies().getOwnedCurrencyMap()).isNotNull();
                Assertions.assertThat(inventory.getOwnedItems()).isNotNull();
                Assertions.assertThat(inventory.getOwnedItems().getOwnedItemMap()).isNotNull();
            });
            return set;
        }).subscribeOn(Schedulers.parallel()).then().block();
    }

    // TODO - Add assertions for OwnedItems and OwnedCurrencies
    @Test
    public void addAndConsumeOwnedItems() {
        Set<Item> generatedItems = IntStream.range(0, 10)
                .mapToObj(i -> EntityGenerators.generateItem()).collect(Collectors.toSet());

        itemsSdk.createSetItem(generatedItems).then().block();
        // Create Items
//       while(!itemsSdk.createSetItem(generatedItems)
//                .subscribe().isDisposed()) {
//
//       }
//        itemSetMono.then().block();
        Mono<Inventory> inventoryMono = inventorySdk.createInventory();
        AtomicReference<Set<Disposable>> disposables = new AtomicReference<>(new HashSet<>());
//        Disposable itemSetDisposable = itemSetMono.subscribe(itemSet -> {
//            Disposable inventoryDisposable = inventoryMono.subscribe(
//                    inventory -> {
//                        Disposable addItemDisposable = inventorySdk.addOwnedItemsInventory(inventory.getUuid(),
//                                itemSet.stream().map(Item::getUuid).collect(Collectors.toSet()),
//                                new Random().nextInt(1, 10))
//                                .subscribe(inv -> System.out.println("Inventory: " + inventory));
//                        disposables.getAndUpdate(v -> {
//                            v.add(addItemDisposable);
//                            return v;
//                        });
//                    });
//            disposables.getAndUpdate(v -> {
//                v.add(inventoryDisposable);
//                return v;
//            });
//        });
//        disposables.getAndUpdate(v -> {
//            v.add(itemSetDisposable);
//            return v;
//        });

        // Create Inventory
        // Add Items to Inventory

        while(disposables.get().size() < 3 && disposables.get().stream().anyMatch(disposable -> !disposable.isDisposed())) {

        }
        System.out.println("COMPLETE");

    }

    @Test
    void creditAndDebitCurrency() {

    }

}
