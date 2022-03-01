package com.firmys.gameservices.world.impl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;

public abstract class InventoryAbstract implements Inventory {
    protected final Map<Currency, AtomicReference<Double>> currencyMap = new ConcurrentHashMap<>();
    protected final Map<Item, ConcurrentLinkedDeque<OwnedItem>> inventoryItems = new ConcurrentHashMap<>();

    public InventoryAbstract(Map<Currency, Double> startingCurrencies) {
        startingCurrencies.forEach((k, v) -> this.currencyMap.put(k, new AtomicReference<>(v)));
    }

    public InventoryAbstract() {
        Map.of(Currency.GOLD, 0D, Currency.COPPER, 0D, Currency.SILVER, 0D, Currency.TIN, 0D)
                .forEach((k, v) -> currencyMap.put(k, new AtomicReference<>(v)));
    }

    @Override
    public Map<Currency, Double> getCurrencies() {
        Map<Currency, Double> tempCurrencies = new HashMap<>();
        currencyMap.forEach((k, v) -> tempCurrencies.put(k, v.get()));
        return tempCurrencies;
    }

    @Override
    public Integer getCurrencyAmount(Currency currency) {
        return currencyMap.get(currency).get().intValue();
    }

    @Override
    public Integer creditCurrency(Currency currency, Double amount) {
        currencyMap.get(currency).set(currencyMap.get(currency).get() + amount);
        return currencyMap.get(currency).get().intValue();
    }

    @Override
    public Integer debitCurrency(Currency currency, Double amount) {
        currencyMap.get(currency).set(currencyMap.get(currency).get() - amount);
        return currencyMap.get(currency).get().intValue();
    }

    @Override
    public Map<Item, ConcurrentLinkedDeque<OwnedItem>> getInventoryItems() {
        return inventoryItems;
    }

    @Override
    public Double getInventoryWeight() {
        return inventoryItems.keySet().stream().map(
                k -> new HashSet<>(inventoryItems.get(k)).stream()
                        .map(ItemAbstract::getWeight)
                        .mapToDouble(Double::doubleValue).sum())
                .mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public OwnedItem getOwnedItem(Item item) {
        return Optional.ofNullable(inventoryItems.get(item).peekLast())
                .orElseThrow(() -> new RuntimeException("Item " + item.getName() + " not in inventory"));
    }

    @Override
    public Double getOwnedItemWeight(Item item) {
        return new HashSet<>(inventoryItems.get(item)).stream().map(ItemAbstract::getWeight)
                .mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public Set<UUID> addOwnedItems(Item item, Integer amount) {
        inventoryItems.putIfAbsent(item, new ConcurrentLinkedDeque<>());
        Set<UUID> uuids = new HashSet<>();
        while(amount > 0) {
            OwnedItem ownedItem = new OwnedItem(item);
            uuids.add(ownedItem.getUuid());
            inventoryItems.get(item).add(ownedItem);
            amount--;
        }
        return uuids;
    }

    @Override
    public UUID addOwnedItem(Item item) {
        return addOwnedItems(item, 1).iterator().next();
    }

    @Override
    public OwnedItem consumeOwnedItem(Item item) {
        return Optional.ofNullable(inventoryItems.get(item).pollLast())
                .orElseThrow(() -> new RuntimeException("Item " + item.getName() + " not in inventory"));
    }

    @Override
    public OwnedItem getOwnedItem(UUID uuid) {
        Item iKey = inventoryItems.keySet().stream().filter(k -> inventoryItems.get(k).stream()
                .anyMatch(i -> i.getUuid().equals(uuid))).findAny().orElseThrow();
        return inventoryItems.get(iKey).stream().filter(
                v -> v.getUuid().equals(uuid)).findFirst().orElseThrow();
    }

    @Override
    public OwnedItem consumeOwnedItem(UUID uuid) {
        Item iKey = inventoryItems.keySet().stream().filter(k -> inventoryItems.get(k).stream()
                .anyMatch(i -> i.getUuid().equals(uuid))).findAny().orElseThrow();
        OwnedItem ownedItem = inventoryItems.get(iKey).stream().filter(v -> v.getUuid().equals(uuid)).findFirst().orElseThrow();
        inventoryItems.get(iKey).remove(ownedItem);
        return ownedItem;
    }

}
