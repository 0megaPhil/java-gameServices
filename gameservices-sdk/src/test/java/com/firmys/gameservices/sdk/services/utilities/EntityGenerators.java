package com.firmys.gameservices.sdk.services.utilities;

import com.firmys.gameservices.common.Formatters;
import com.firmys.gameservices.models.*;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.models.Currency;
import com.github.javafaker.Faker;

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
    public static Faker faker = new Faker();

    public static Set<Inventory> generateInventories(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(i -> generateInventory()).collect(Collectors.toSet());
    }

    public static Inventory generateInventory() {
        Set<Item> itemSet = IntStream.range(0, new Random().nextInt(0, 256))
                .mapToObj(i -> generateItem()).collect(Collectors.toSet());

        Set<Currency> currencySet = IntStream.range(0, new Random().nextInt(0, 256))
                .mapToObj(i -> generateCurrency()).collect(Collectors.toSet());
        Set<ConsumableItem> consumableItems = generateConsumableItems(itemSet);
        Set<TransactionalCurrency> transactionalCurrencies = generateTransactionalCurrencies(currencySet);

        Inventory inventory = new Inventory();
        inventory.setConsumableItems(consumableItems);
        inventory.setTransactionalCurrencies(transactionalCurrencies);
        return inventory;
    }

    public static Set<ConsumableItem> generateConsumableItems(Set<Item> items) {
        return items.stream().map(EntityGenerators::generateConsumableItem).collect(Collectors.toSet());
    }

    public static ConsumableItem generateConsumableItem(Item item) {
        ConsumableItem consumableItem = new ConsumableItem();
        consumableItem.setItemUuid(item.getUuid());
        consumableItem.setConsumables(IntStream.range(0, new Random().nextInt(1, 10))
                .mapToObj(i -> {
                    Consumable consumable = new Consumable();
                    consumable.setConsumableType(ConsumableItem.class.getSimpleName());
                    consumable.setCreated(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
                    return consumable;
                }).collect(Collectors.toSet()));
        consumableItem.setOwnedCount(consumableItem.getConsumables().size());
        return consumableItem;
    }

    public static Set<TransactionalCurrency> generateTransactionalCurrencies(Set<Currency> currencySet) {
        return currencySet.stream().map(EntityGenerators::generateTransactionalCurrency).collect(Collectors.toSet());
    }

    public static TransactionalCurrency generateTransactionalCurrency(Currency currency) {
        TransactionalCurrency transactionalCurrency = new TransactionalCurrency();
        transactionalCurrency.setCurrencyUuid(currency.getUuid());
        transactionalCurrency.setTotalCurrency(new Random().nextLong(1, Long.MAX_VALUE));
        transactionalCurrency.setTransactions(IntStream
                .range(0, new Random().nextInt(1, 10)).mapToObj(i -> generateTransaction(currency)).collect(Collectors.toSet()));
        return transactionalCurrency;
    }

    public static Transaction generateTransaction(Currency currency) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(new Random().nextInt() % 2 == 0 ? "CREDIT" : "DEBIT");
        long amount = numericSup.get().longValue();
        transaction.setStart(numericSup.get().longValue());
        transaction.setAmount(amount);
        transaction.setResult(amount + transaction.getStart());
        LocalDateTime localDateTime = LocalDateTime.now();
        transaction.setDateTime(Formatters.dateTimeFormatter.format(localDateTime.atOffset(ZoneOffset.UTC)));
        transaction.setDate(Formatters.dateFormatter.format(localDateTime.atOffset(ZoneOffset.UTC)));
        transaction.setCurrencyUuid(currency.getUuid());
        return transaction;
    }

    public static Currency generateCurrency() {
        Currency currency = new Currency();
        currency.setName(faker.currency().name());
        currency.setDescription(faker.currency().code());
        currency.setBaseValue(new Random().nextInt(1, 10000000));
        return currency;
    }

    public static Item generateItem() {
        Item item = new Item();
        item.setName(faker.book().title() + " - " + alphaSup.get());
        item.setDescription(faker.book().author() + ": " + faker.book().genre() + " - " + faker.book().publisher());
        item.setBaseValue(numericSup.get());
        item.setHeight(numericSup.get());
        item.setLength(numericSup.get());
        item.setWidth(numericSup.get());
        item.setWeight(doubleSup.get());
        item.setItemRequirements(generateItemRequirements());
        return item;
    }

    public static Set<ItemRequirement> generateItemRequirements() {
        return Arrays.stream(ItemRequirement.AttributeEnum.values()).map(a -> {
            ItemRequirement attr = new ItemRequirement();
            attr.setAttribute(a);
            attr.setValue(new Random().nextInt(1, 255));
            return attr;
        }).collect(Collectors.toSet());
    }

    public static Set<CharacterAttribute> generateCharacterAttributes() {
        return Arrays.stream(CharacterAttribute.AttributeEnum.values()).map(a -> {
            CharacterAttribute attr = new CharacterAttribute();
            attr.setAttribute(a);
            attr.setValue(new Random().nextInt(1, 255));
            return attr;
        }).collect(Collectors.toSet());
    }

    public static Character generateCharacter() {
        Character character = new Character();
        character.setName(faker.elderScrolls().firstName() + " " + faker.elderScrolls().lastName() + " - " + alphaSup.get());
        character.setDescription(String.join(", ", faker.elderScrolls().race(),
                faker.elderScrolls().city(), faker.elderScrolls().quote()));
        character.setHeight(numericSup.get());
        character.setWeight(numericSup.get());
        character.setAge(numericSup.get());
        character.setGender(faker.animal().name());
        character.setCharacterAttributes(generateCharacterAttributes());
        return character;
    }

}
