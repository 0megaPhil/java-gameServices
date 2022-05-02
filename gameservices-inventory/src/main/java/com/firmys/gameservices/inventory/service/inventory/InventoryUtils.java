package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.inventory.service.data.*;

public class InventoryUtils {

    public static Inventory consumeOwnedItemByItem(Item item, Inventory inventory, int amount) {
        try {
            inventory.setOwnedItems(inventory.getOwnedItems().consumeItem(item, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceConstants.CONSUME,
                    ServiceConstants.ITEM + ": " + item.getUuid(),
                    ServiceConstants.INVENTORY + ": " + inventory.getUuid(),
                    ServiceConstants.AMOUNT + ": " + amount);
        }
    }

    public static Inventory addOwnedItemByItemUuid(Item item, Inventory inventory, int amount) {
        try {
            inventory.setOwnedItems(inventory.getOwnedItems().addItem(item, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceConstants.ADD,
                    ServiceConstants.ITEM + ": " + item.getUuid(),
                    ServiceConstants.INVENTORY + ": " + inventory.getUuid(),
                    ServiceConstants.AMOUNT + ": " + amount);
        }
    }

    public static Inventory creditCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            inventory.setOwnedCurrencies(inventory.getOwnedCurrencies().creditCurrency(currency, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceConstants.CREDIT,
                    ServiceConstants.CURRENCY + ": " + currency.getUuid(),
                    ServiceConstants.INVENTORY + ": " + inventory.getUuid(),
                    ServiceConstants.AMOUNT + ": " + amount);
        }
    }

    public static Inventory debitCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            inventory.setOwnedCurrencies(inventory.getOwnedCurrencies().debitCurrency(currency, amount));
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceConstants.DEBIT,
                    ServiceConstants.CURRENCY + ": " + currency.getUuid(),
                    ServiceConstants.INVENTORY + ": " + inventory.getUuid(),
                    ServiceConstants.AMOUNT + ": " + amount);
        }
    }
}
