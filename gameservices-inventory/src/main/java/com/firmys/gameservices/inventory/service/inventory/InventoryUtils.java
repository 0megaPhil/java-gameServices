package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.inventory.service.data.*;

import java.util.Set;
import java.util.stream.Collectors;

public class InventoryUtils {
    private static final GameServiceException.Builder exceptionBuilder = GameServiceException.builder
            .withGameDataType(Inventory.class);
    private static final GameServiceError.Builder errorBuilder = GameServiceError.builder
            .withName(ServicePaths.INVENTORY);

    public static Inventory consumeOwnedItemByItemUuid(Item item, Inventory inventory) {
        return consumeOwnedItemByItemUuid(item, inventory, 1);
    }

    public static Inventory consumeOwnedItemByItemUuid(Item item, Inventory inventory, int amount) {
        try {
            inventory.setOwnedItems(inventory.getOwnedItems().consumeItem(item, amount));
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Inventory addOwnedItemByItemUuid(Item item, Inventory inventory) {
        return addOwnedItemByItemUuid(item, inventory, 1);
    }

    public static Inventory addOwnedItemByItemUuid(Item item, Inventory inventory, int amount) {
        try {
            inventory.setOwnedItems(inventory.getOwnedItems().addItem(item, amount));
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Set<Inventory> getInventoriesWithItem(Set<Inventory> inventories, Item item) {
        try {
            return inventories.stream()
                    .filter(i -> i.getOwnedItems().getOwnedItemByItem(item).getCount() > 0)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Inventory creditCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            inventory.setOwnedCurrencies(inventory.getOwnedCurrencies().creditCurrency(currency, amount));
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Inventory debitCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            inventory.setOwnedCurrencies(inventory.getOwnedCurrencies().debitCurrency(currency, amount));
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }
}
