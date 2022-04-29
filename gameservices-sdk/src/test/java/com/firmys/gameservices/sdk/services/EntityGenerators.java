package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.common.Formatters;
import com.firmys.gameservices.models.*;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.models.Currency;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityGenerators {
    public static Supplier<String> alphaNumericSup = SdkTestUtilities.RandomString::getAlphaNumeric;
    public static Supplier<String> alphaSup = SdkTestUtilities.RandomString::getAlpha;
    public static Supplier<Integer> numericSup = SdkTestUtilities.RandomString::getNumeric;
    public static Supplier<Double> doubleSup = SdkTestUtilities.RandomString::getDouble;
    public static Supplier<UUID> uuid = UUID::randomUUID;

    public static Set<Inventory> generateInventories(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> generateInventory()).collect(Collectors.toSet());
    }

    public static Inventory generateInventory() {
        Set<Item> itemSet = IntStream.range(0, new Random().nextInt(0, 256))
                .mapToObj(i -> generateItem()).collect(Collectors.toSet());

        Set<Currency> currencySet = IntStream.range(0, new Random().nextInt(0, 256))
                .mapToObj(i -> generateCurrency()).collect(Collectors.toSet());
        OwnedItems ownedItems = generateOwnedItems(itemSet);
        OwnedCurrencies ownedCurrencies = generateOwnedCurrencies(currencySet);

        Inventory inventory = new Inventory();
        inventory.setUuid(UUID.randomUUID());
        inventory.setOwnedItems(ownedItems);
        inventory.setOwnedCurrencies(ownedCurrencies);
        return inventory;
    }

    public static OwnedItems generateOwnedItems(Set<Item> items) {
        Set<OwnedItem> ownedItemSet = items.stream()
                .map(EntityGenerators::generateOwnedItem).collect(Collectors.toSet());
        Map<String, OwnedItem> ownedItemMap = ownedItemSet.stream()
                .map(o -> Map.entry(o.getItemUuid().toString(), o))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        OwnedItems ownedItems = new OwnedItems();
        ownedItems.setOwnedItemMap(ownedItemMap);
        ownedItems.setUuid(uuid.get());
        return ownedItems;
    }

    public static OwnedItem generateOwnedItem(Item item) {
        OwnedItem ownedItem = new OwnedItem();
        ownedItem.setItemUuid(item.getUuid());
        ownedItem.setOwnedItemUuids(IntStream.range(0, new Random().nextInt(1, 10))
                .mapToObj(i -> UUID.nameUUIDFromBytes((item.getName() + i).getBytes())).collect(Collectors.toSet()));
        ownedItem.setCount(ownedItem.getOwnedItemUuids().size());
        return ownedItem;
    }

    public static OwnedCurrencies generateOwnedCurrencies(Set<Currency> currencySet) {
        Set<OwnedCurrency> ownedCurrencySet = currencySet.stream()
                .map(EntityGenerators::generateOwnedCurrency).collect(Collectors.toSet());

        OwnedCurrencies ownedCurrencies = new OwnedCurrencies();
        ownedCurrencies.setUuid(UUID.randomUUID());
        ownedCurrencies.setOwnedCurrencyMap(ownedCurrencySet.stream()
                .map(c -> Map.entry(c.getCurrencyUuid().toString(), c))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return ownedCurrencies;
    }

    public static OwnedCurrency generateOwnedCurrency(Currency currency) {
        OwnedCurrency ownedCurrency = new OwnedCurrency();
        ownedCurrency.setCurrencyUuid(currency.getUuid());
        ownedCurrency.setCount(new Random().nextLong(1, Long.MAX_VALUE));
        ownedCurrency.setTransactions(IntStream
                .range(0, new Random().nextInt(1, 10)).mapToObj(i -> generateTransaction()).collect(Collectors.toSet()));
        return ownedCurrency;
    }

    public static Transaction generateTransaction() {
        Transaction transaction = new Transaction();
        long entryNum = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() % Transaction.TransactionTypeEnum.values().length;
        Transaction.TransactionTypeEnum typeEnum = Transaction.TransactionTypeEnum.values()[(int) entryNum];
        transaction.setTransactionType(typeEnum);
        long amount = numericSup.get().longValue();
        transaction.setStart(numericSup.get().longValue());
        transaction.setAmount(amount);
        transaction.setEnd(amount + transaction.getStart());
        LocalDateTime localDateTime = LocalDateTime.now();
        transaction.setDateTime(Formatters.dateTimeFormatter.format(localDateTime.atOffset(ZoneOffset.UTC)));
        transaction.setDate(Formatters.dateFormatter.format(localDateTime.atOffset(ZoneOffset.UTC)));
        transaction.setCurrencyUuid(UUID.randomUUID());
        return transaction;
    }

    public static Currency generateCurrency() {
        Currency currency = new Currency();
        currency.setUuid(uuid.get());
        currency.setName(alphaSup.get());
        currency.setDescription(alphaNumericSup.get());
        currency.setBaseValue(new Random().nextInt(1, 10000000));
        return currency;
    }

    public static Item generateItem() {
        Item item = new Item();
        item.setName(alphaSup.get());
        item.setDescription(alphaNumericSup.get());
        item.setUuid(uuid.get());
        item.setBaseValue(numericSup.get());
        item.setHeight(numericSup.get());
        item.setLength(numericSup.get());
        item.setWidth(numericSup.get());
        item.setWeight(doubleSup.get());
        item.setRequirements(alphaNumericSup.get());
        return item;
    }

    public static Character generateCharacter() {
        Character character = new Character();
        character.setName("ddd");
        character.setDescription("ffsfed");
        character.setHeight(1);
        character.setWeight(1);
        character.setAge(1);
        character.setGender("thing");
        return character;
    }

}
