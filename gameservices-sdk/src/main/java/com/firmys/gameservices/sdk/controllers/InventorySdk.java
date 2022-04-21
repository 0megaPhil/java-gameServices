package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.api.InventoryApi;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.models.OwnedItems;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public class InventorySdk implements InventoryApi {
    @Override
    public Mono<Inventory> addInventory(Inventory inventory) {
        return null;
    }

    @Override
    public Mono<Inventory> addOwnedItemInventory(String uuid, Integer amount, Item item) {
        return null;
    }

    @Override
    public Mono<Inventory> addOwnedItemsInventory(String uuid, Integer amount, List<Item> item) {
        return null;
    }

    @Override
    public Mono<Inventory> consumeOwnedItemInventory(String uuid, Integer amount, Item item) {
        return null;
    }

    @Override
    public Mono<Inventory> consumeOwnedItemsInventory(String uuid, Integer amount, List<Item> item) {
        return null;
    }

    @Override
    public Mono<Inventory> creditCurrencyByUuidInventory(String uuid, Integer amount, Currency currency) {
        return null;
    }

    @Override
    public Mono<Inventory> debitCurrencyByUuidInventory(String uuid, Integer amount, Currency currency) {
        return null;
    }

    @Override
    public Mono<Void> deleteByUuidInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteInventory(String uuid, Inventory inventory) {
        return null;
    }

    @Override
    public Mono<Inventory> findByUuidParamInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Inventory> findByUuidPathInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Set<Inventory>> findInventoriesWithItemByUuidParamInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Set<Inventory>> findInventoriesWithItemByUuidPathInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<OwnedItems> getOwnedItemsByInventoryUuidInventory(String uuid, Inventory requestBody) {
        return null;
    }

    @Override
    public Mono<Inventory> updateByUuidInventory(String uuid, Inventory inventory) {
        return null;
    }

    @Override
    public Mono<Inventory> updateInventory(Inventory inventory) {
        return null;
    }
}
