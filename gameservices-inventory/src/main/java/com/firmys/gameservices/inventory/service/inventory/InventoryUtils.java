package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.inventory.service.data.Currency;
import com.firmys.gameservices.inventory.service.data.Inventory;
import com.firmys.gameservices.inventory.service.data.Item;
import com.firmys.gameservices.inventory.service.data.OwnedCurrency;
import com.firmys.gameservices.inventory.service.data.OwnedItem;

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
            Set<OwnedItem> ownedItemSet = inventory.getOwnedItems();
            OwnedItem ownedItem = ownedItemSet.stream()
                    .filter(o -> o.getItemUuid().equals(item.getUuid())).findFirst().orElse(new OwnedItem(item));
            if(ownedItem.consume(amount).getCount() < 1) {
                ownedItemSet.remove(ownedItem);
            }
            inventory.setOwnedItems(ownedItemSet);
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
            Set<OwnedItem> ownedItemSet = inventory.getOwnedItems();
            OwnedItem ownedItem = ownedItemSet.stream()
                    .filter(o -> o.getItemUuid().equals(item.getUuid())).findFirst().orElse(new OwnedItem(item));
            ownedItem.add(amount);
            ownedItemSet.add(ownedItem);
            inventory.setOwnedItems(ownedItemSet);
            return inventory;
        } catch (Exception e) {
            throw exceptionBuilder.withGameServiceError(
                    errorBuilder.withThrowable(e)
                            .withDescription(e.getMessage()).build()).build();
        }
    }

    // FIXME - update
    public static Set<Inventory> getInventoriesWithItem(Set<Inventory> inventories, Item item) {
        try {
            return inventories.stream()
                    .filter(i -> i.getOwnedItems().stream().filter(o -> o.getItemUuid() != null)
                            .anyMatch(o -> o.getItemUuid()
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
