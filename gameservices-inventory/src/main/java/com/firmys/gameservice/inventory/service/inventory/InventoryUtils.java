package com.firmys.gameservice.inventory.service.inventory;

import com.firmys.gameservice.common.ErrorMessages;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.common.error.GameServiceError;
import com.firmys.gameservice.common.error.GameServiceException;
import com.firmys.gameservice.inventory.service.data.*;

import java.util.Set;
import java.util.stream.Collectors;

public class InventoryUtils {
    private static final GameServiceException.Builder exceptionBuilder = GameServiceException.builder
            .withGameDataType(Inventory.class);
    private static final GameServiceError.Builder errorBuilder = GameServiceError.builder
            .withName(ServiceConstants.INVENTORY);

    public static Inventory consumeOwnedItemByItemUuid(Item item, Inventory inventory) {
        try {
            Set<OwnedItem> ownedItems = inventory.getOwnedItems();
            OwnedItem consumable = ownedItems.stream().filter(i -> i.getItem().getUuid().toString()
                            .equals(item.getUuid().toString())).findFirst()
                    .orElseThrow(() -> GameServiceException.builder.withGameDataType(OwnedItem.class)
                            .withGameServiceError(GameServiceError.builder
                                    .withDescription(ErrorMessages.notFoundByUuid(item.getUuid().toString())
                                            .apply(OwnedItem.class)).build()).build());
            ownedItems.remove(consumable);
            inventory.setOwnedItems(ownedItems);
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Inventory addOwnedItemByItemUuid(Item item, Inventory inventory) {
        try {
            Set<OwnedItem> ownedItems = inventory.getOwnedItems();
            ownedItems.add(new OwnedItem(item));
            inventory.setOwnedItems(ownedItems);
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
                    .filter(i -> i.getOwnedItems().stream().filter(o -> o.getItem() != null)
                            .anyMatch(o -> o.getItem().getUuid()
                                    .toString().equals(item.getUuid().toString())))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Inventory creditCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            OwnedCurrency ownedCurrency = inventory.getOwnedCurrency();
            ownedCurrency.creditCurrency(currency, amount);
            inventory.setOwnedCurrency(ownedCurrency);
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    public static Inventory debitCurrency(Currency currency, Inventory inventory, int amount) {
        try {
            OwnedCurrency ownedCurrency = inventory.getOwnedCurrency();
            ownedCurrency.debitCurrency(currency, amount);
            inventory.setOwnedCurrency(ownedCurrency);
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }
}
