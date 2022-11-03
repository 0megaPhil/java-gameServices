package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.*;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.sdk.config.SdkConfig;
import com.firmys.gameservices.sdk.services.utilities.EntityGenerators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = {SdkConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CharacterSdkIT extends SdkBase {

    @Autowired
    CharacterSdk sdk;

    @Autowired
    InventorySdk inventorySdk;

    @Autowired
    ItemSdk itemSdk;

    @Autowired
    CurrencySdk currencySdk;

    AtomicReference<Character> characterRef = new AtomicReference<>();
    AtomicReference<Inventory> inventoryRef = new AtomicReference<>();
    AtomicReference<Item> itemRef = new AtomicReference<>();
    AtomicReference<Currency> currencyRef = new AtomicReference<>();

    @BeforeAll
    void generate() {
        inventorySdk.createInventory().map(i -> {
            inventoryRef.set(i);
            return i;
        }).retry(5).then().block();

        sdk.createCharacter(EntityGenerators.generateCharacter()).map(i -> {
            characterRef.set(i);
            return i;
        }).retry(5).then().block();

        itemSdk.createItem(EntityGenerators.generateItem()).map(i -> {
            itemRef.set(i);
            return i;
        }).retry(5).then().block();

        currencySdk.createCurrency(EntityGenerators.generateCurrency()).map(i -> {
            currencyRef.set(i);
            return i;
        }).retry(5).then().block();

        Character character = characterRef.get();
        character.setInventoryId(inventoryRef.get().getUuid());
        // Add InventoryId to Character
        sdk.updateCharacter(character).map(c -> {
            characterRef.set(c);
            return c;
        }).retry(5).then().block();

    }

    @Test
    void createCharacterFlow() {

        // Add OwnedItem
        inventorySdk.addConsumableItemInventory(inventoryRef.get().getUuid(),
                        itemRef.get().getUuid(), new Random().nextInt(1, 9))
                .retry(5)
                .then().block();

        // Add OwnedCurrency
        inventorySdk.creditTransactionalCurrencyInventory(inventoryRef.get().getUuid(),
                currencyRef.get().getUuid(), new Random().nextInt(1, 255))
                .retry(5)
                .then().block();

        // Verify Character
        sdk.findCharacter(characterRef.get().getUuid()).map(c -> {
            characterRef.set(c);
            return c;
        }).retry(5).then().block();

        Character updated = characterRef.get();
        updated.setInventoryId(inventoryRef.get().getUuid());
        Set<CharacterAttribute> characterAttributes = updated.getCharacterAttributes();
        characterAttributes.forEach(c -> c.setValue(c.getValue() + new Random().nextInt(1, 9)));
        updated.setCharacterAttributes(characterAttributes);
        sdk.updateCharacter(updated).retry(5).then().block();

        System.out.println("Character: " + characterRef.get());

    }


}
