//package com.firmys.gameservices.sdk.controllers;
//
//import com.firmys.gameservices.models.*;
//import com.firmys.gameservices.models.Currency;
//
//import java.util.*;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class InventoryTestUtilities {
//    public static Supplier<String> alphaNumericSup = SdkTestUtilities.RandomString::getAlphaNumeric;
//    public static Supplier<String> alphaSup = SdkTestUtilities.RandomString::getAlpha;
//    public static Supplier<Integer> numericSup = SdkTestUtilities.RandomString::getNumeric;
//    public static Supplier<Double> doubleSup = SdkTestUtilities.RandomString::getDouble;
//    public static Supplier<UUID> uuid = UUID::randomUUID;
//
//    public static Inventory generateInventory() {
//        Set<Item> itemSet = IntStream.range(0, new Random().nextInt(0, 256))
//                .mapToObj(i -> generateItem()).collect(Collectors.toSet());
//        OwnedItems ownedItems = generateOwnedItems(itemSet);
//
//        Inventory inventory = new Inventory();
//
//    }
//
//    public static OwnedItems generateOwnedItems(Set<Item> items) {
//        Set<OwnedItem> ownedItemSet = items.stream().map(item ->
//                generateOwnedItem(item, new Random().nextInt(0, 10))).collect(Collectors.toSet());
//        Map<String, OwnedItem> ownedItemMap = ownedItemSet.stream()
//                .map(o -> Map.entry(o.getItemUuid().toString(), o))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        OwnedItems ownedItems = new OwnedItems();
//        ownedItems.setOwnedItemMap(ownedItemMap);
//        ownedItems.setUuid(uuid.get());
//        return ownedItems;
//    }
//
//    public static OwnedItem generateOwnedItem(Item item, int count) {
//        OwnedItem ownedItem = new OwnedItem();
//        ownedItem.setItemUuid(item.getUuid());
//        ownedItem.setCount(count);
//        ownedItem.setUuids(IntStream.range(0, count)
//                .mapToObj(i -> UUID.fromString(item.getName() + i)).collect(Collectors.toSet()));
//        return ownedItem;
//    }
//
//    public static OwnedCurrency generateOwnedCurrency(Currency currency) {
//        OwnedCurrency ownedCurrency = new OwnedCurrency();
//
//        ownedCurrency.setCurrencyUuid(currency.getUuid());
//
//        Map<String, Currency> currencyMap = currencySet.stream()
//                .map(o -> Map.entry(o.getUuid().toString(), o))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//
//        ownedCurrency.setCurrencyOwnedMap();
//    }
//
//    public static Currency generateCurrency() {
//        Currency currency = new Currency();
//        currency.setUuid(uuid.get());
//        currency.setName(alphaSup.get());
//        currency.setDescription(alphaNumericSup.get());
//        currency.setBaseValue(new Random().nextInt(1, 10000000));
//        return currency;
//    }
//
//    public static Item generateItem() {
//        Item item = new Item();
//        item.setName(alphaSup.get());
//        item.setDescription(alphaNumericSup.get());
//        item.setUuid(uuid.get());
//        item.setBaseValue(numericSup.get());
//        item.setHeight(numericSup.get());
//        item.setLength(numericSup.get());
//        item.setWidth(numericSup.get());
//        item.setWeight(doubleSup.get());
//        item.setRequirements(alphaNumericSup.get());
//        return item;
//    }
//
//
//}
