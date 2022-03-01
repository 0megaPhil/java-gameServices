package com.firmys.gameservices.world.impl;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface Inventory {
    Map<Currency, Double> getCurrencies();
    Integer getCurrencyAmount(Currency currency);
    Integer creditCurrency(Currency currency, Double amount);
    Integer debitCurrency(Currency currency, Double amount);
    Map<Item, ConcurrentLinkedDeque<OwnedItem>> getInventoryItems();
    Double getInventoryWeight();
    Double getOwnedItemWeight(Item item);
    OwnedItem getOwnedItem(Item item);
    OwnedItem consumeOwnedItem(Item item);
    Set<UUID> addOwnedItems(Item item, Integer amount);
    UUID addOwnedItem(Item item);
    OwnedItem getOwnedItem(UUID uuid);
    OwnedItem consumeOwnedItem(UUID uuid);

}
