package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.inventory.service.data.*;

public class InventoryTransaction {

    public static Inventory consumeOwnedItemByItem(Item item, Inventory inventory, int amount) {
        try {
            inventory.getConsumableItems().stream()
                    .filter(c -> c.getItemUuid().equals(item.getUuid())).findFirst().orElseThrow(() ->
                            new RuntimeException("No ConsumableItem matching " + item.getUuid() +
                                    " found in Inventory " + inventory.getUuid()))
                    .consume(amount);
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
            if(inventory.getConsumableItems().stream().noneMatch(c -> c.getItemUuid().equals(item.getUuid()))) {
                inventory.getConsumableItems().add(new ConsumableItem(inventory, item));
            }
            inventory.getConsumableItems().stream()
                    .filter(c -> c.getItemUuid().equals(item.getUuid())).findFirst().orElseThrow()
                    .add(amount);
            System.out.println("" + inventory.getConsumableItems());
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
            if(inventory.getTransactionalCurrencies().stream().noneMatch(c -> c.getCurrencyUuid().equals(currency.getUuid()))) {
                inventory.getTransactionalCurrencies().add(new TransactionalCurrency(inventory, currency));
            }
            inventory.getTransactionalCurrencies().stream()
                    .filter(c -> c.getCurrencyUuid().equals(currency.getUuid())).findFirst().orElseThrow()
                    .credit(amount);
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
            inventory.getTransactionalCurrencies().stream()
                    .filter(c -> c.getCurrencyUuid().equals(currency.getUuid())).findFirst().orElseThrow(() ->
                            new RuntimeException("No TransactionalCurrency matching " + currency.getUuid() +
                                    " found in Inventory " + inventory.getUuid()))
                    .debit(amount);
            return inventory;
        } catch (Exception e) {
            throw new GameServiceException(e, ServiceConstants.DEBIT,
                    ServiceConstants.CURRENCY + ": " + currency.getUuid(),
                    ServiceConstants.INVENTORY + ": " + inventory.getUuid(),
                    ServiceConstants.AMOUNT + ": " + amount);
        }
    }
}
