package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.inventory.service.data.*;

public class InventoryUtils {

    public static Inventory consumeOwnedItemByItem(Item item, Inventory inventory, int amount) {
        try {
            inventory.setOwnedItems(inventory.getOwnedItems().consumeItem(item, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceStrings.CONSUME,
                    ServiceStrings.ITEM + ": " + item.getUuid(),
                    ServiceStrings.INVENTORY + ": " + inventory.getUuid(),
                    ServiceStrings.AMOUNT + ": " + amount);
        }
    }

    public static Inventory addOwnedItemByItemUuid(Item item, Inventory inventory, int amount) {
        try {
            inventory.setOwnedItems(inventory.getOwnedItems().addItem(item, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceStrings.ADD,
                    ServiceStrings.ITEM + ": " + item.getUuid(),
                    ServiceStrings.INVENTORY + ": " + inventory.getUuid(),
                    ServiceStrings.AMOUNT + ": " + amount);
        }
    }

    public static Inventory creditCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            inventory.setOwnedCurrencies(inventory.getOwnedCurrencies().creditCurrency(currency, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceStrings.CREDIT,
                    ServiceStrings.CURRENCY + ": " + currency.getUuid(),
                    ServiceStrings.INVENTORY + ": " + inventory.getUuid(),
                    ServiceStrings.AMOUNT + ": " + amount);
        }
    }

    public static Inventory debitCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            inventory.setOwnedCurrencies(inventory.getOwnedCurrencies().debitCurrency(currency, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceStrings.DEBIT,
                    ServiceStrings.CURRENCY + ": " + currency.getUuid(),
                    ServiceStrings.INVENTORY + ": " + inventory.getUuid(),
                    ServiceStrings.AMOUNT + ": " + amount);
        }
    }
}
