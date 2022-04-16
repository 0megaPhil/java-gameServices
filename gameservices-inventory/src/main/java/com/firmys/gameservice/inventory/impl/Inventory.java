package com.firmys.gameservice.inventory.impl;

import com.firmys.gameservice.inventory.service.data.Item;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface Inventory {
    Map<Currency, Double> getCurrencies();
    Integer getCurrencyAmount(Currency currency);
    Integer creditCurrency(Currency currency, Double amount);
    Integer debitCurrency(Currency currency, Double amount);
    Map<Item, ConcurrentLinkedDeque<OwnedItemObject>> getInventoryItems();
    Double getInventoryWeight();
    Double getOwnedItemWeight(Item item);
    OwnedItemObject getOwnedItem(Item item);
    OwnedItemObject consumeOwnedItem(Item item);
    Set<UUID> addOwnedItems(Item item, Integer amount);
    UUID addOwnedItem(Item item);
    OwnedItemObject getOwnedItem(UUID uuid);
    OwnedItemObject consumeOwnedItem(UUID uuid);

}
