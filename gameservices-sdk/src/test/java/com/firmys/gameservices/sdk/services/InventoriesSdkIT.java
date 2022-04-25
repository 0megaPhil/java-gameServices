package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.config.SdkConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = {SdkConfig.class})
public class InventoriesSdkIT {

    @Autowired
    InventoriesSdk inventoriesSdk;

    @Test
    public void createAndDeleteInventories() {
        Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(2).block();

        Assertions.assertThat(addedSet).isNotNull();
        Assertions.assertThat(addedSet.size()).isEqualTo(2);
        addedSet.forEach(i -> {
            Assertions.assertThat(i.getUuid()).isNotNull();
            Assertions.assertThat(i.getOwnedCurrencies()).isNotNull();
            Assertions.assertThat(i.getOwnedCurrencies().getOwnedCurrencyMap()).isNotNull();
            Assertions.assertThat(i.getOwnedItems()).isNotNull();
            Assertions.assertThat(i.getOwnedItems().getOwnedItemMap()).isNotNull();
        });

        inventoriesSdk.deleteMultipleInventory(new HashSet<>(), addedSet).block();

        Set<Inventory> foundSet = inventoriesSdk.findMultipleInventory(null).block();
        Assertions.assertThat(Objects.requireNonNull(foundSet).stream()
                .map(i -> i.getUuid().toString()).collect(Collectors.toSet())).doesNotContainAnyElementsOf(addedSet.stream()
                .map(i -> i .getUuid().toString()).collect(Collectors.toSet()));
    }

}
