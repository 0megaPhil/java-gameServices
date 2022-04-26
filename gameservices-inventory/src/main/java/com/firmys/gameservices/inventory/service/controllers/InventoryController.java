package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.inventory.service.data.*;
import com.firmys.gameservices.inventory.service.data.Currency;
import com.firmys.gameservices.inventory.service.inventory.InventoryDataLookup;
import com.firmys.gameservices.inventory.service.inventory.InventoryService;
import com.firmys.gameservices.inventory.service.inventory.InventoryUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class InventoryController extends AbstractController<Inventory> {
    private final ItemController itemController;
    private final CurrencyController currencyController;

    public InventoryController(
            InventoryService inventoryService,
            InventoryDataLookup dataLookup,
            ItemController itemController,
            CurrencyController currencyController) {
        super(inventoryService, dataLookup, Inventory.class, Inventory::new);
        this.itemController = itemController;
        this.currencyController = currencyController;
    }

    /**
     * {@link ServiceStrings#INVENTORIES_PATH}
     * Multiple methods do not support UUID as path variable
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServiceStrings.INVENTORIES_PATH)
    public Set<Inventory> findMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    /**
     * A bit of a special case, as Inventory have no identitifying attributes, and so creating bulk is handled differently
     * @param amount number of {@link Inventory} to create
     * @return set of newly generated Inventory objects
     */
    @PostMapping(value = ServiceStrings.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> addMultiple(
            @RequestParam(value = ServiceStrings.AMOUNT) int amount) {
        return IntStream.range(0, amount).mapToObj(i -> super.save(new Inventory())).collect(Collectors.toSet());
    }

    @DeleteMapping(value = ServiceStrings.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Inventory> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServiceStrings.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> updateMultiple(
            @RequestBody Set<Inventory> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#INVENTORY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServiceStrings.INVENTORY_PATH)
    public Inventory findByUuidParam(
            @RequestParam(value = ServiceStrings.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Inventory findByUuidPath(
            @PathVariable(ServiceStrings.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServiceStrings.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory add(@RequestBody(required = false) Inventory entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Inventory entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServiceStrings.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory update(@RequestBody Inventory entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory updateByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid,
                                  @RequestBody Inventory entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

    /**
     * Complex control methods
     * Singular Inventory
     */
    @PutMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.CURRENCY_PATH)
    public Inventory creditCurrencyByUuid(
            @PathVariable(value = ServiceStrings.UUID, required = true) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) String currencyUuid,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amountParam,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.creditCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid)),
                super.findByUuid(UUID.fromString(uuid)), amountParam));
    }

    @DeleteMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.CURRENCY_PATH)
    public Inventory debitCurrencyByUuid(
            @PathVariable(value = ServiceStrings.UUID, required = true) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) String currencyUuid,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amountParam,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.debitCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid)),
                super.findByUuid(UUID.fromString(uuid)),
                amountParam));
    }

    @GetMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Set<Inventory> findInventoriesWithItemByUuidPath(
            @PathVariable(value = ServiceStrings.UUID, required = false) String uuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(UUID.fromString(uuid)));
    }

    @GetMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.ITEM_PATH)
    public Set<Inventory> findInventoriesWithItemByUuidParam(
            @RequestParam(value = ServiceStrings.UUID, required = false) String itemUuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(UUID.fromString(itemUuid)));
    }


    @PutMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.ITEM_PATH)
    public Inventory addOwnedItem(
            @PathVariable(ServiceStrings.UUID) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestParam(value = ServiceStrings.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.addOwnedItemByItemUuid(itemController
                        .findByUuid(getUuidFromBodyOrParam(requestBody, uuidParam)),
                super.findByUuid(UUID.fromString(uuid)), Optional.ofNullable(amount).orElse(1)));
    }

    // FIXME issues when
    @PutMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.ITEMS_PATH)
    public Inventory addOwnedItems(
            @PathVariable(ServiceStrings.UUID) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams,
            @RequestParam(value = ServiceStrings.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) List<Item> requestBodies) {
        Inventory inventory = super.findByUuid(UUID.fromString(uuid));
        Set<String> itemUuids = Optional.ofNullable(uuidParams).orElse(new HashSet<>());
        itemUuids.addAll(Optional.ofNullable(requestBodies)
                .orElse(new ArrayList<>()).stream().map(i -> i.getUuid().toString()).collect(Collectors.toSet()));
        for(String itemUuid: itemUuids) {
            InventoryUtils.addOwnedItemByItemUuid(itemController
                            .findByUuid(UUID.fromString(itemUuid)), inventory,
                    Optional.ofNullable(amount).orElse(1));
        }
        return super.save(inventory);
    }

    @DeleteMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.ITEM_PATH)
    public Inventory consumeOwnedItem(
            @PathVariable(ServiceStrings.UUID) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestParam(value = ServiceStrings.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.consumeOwnedItemByItemUuid(
                itemController.findByUuid(getUuidFromBodyOrParam(
                        requestBody, uuidParam)), super.findByUuid(UUID.fromString(uuid)),
                Optional.ofNullable(amount).orElse(1)));
    }

    @DeleteMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.ITEMS_PATH)
    public Inventory consumeOwnedItems(
            @PathVariable(ServiceStrings.UUID) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestParam(value = ServiceStrings.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) List<Item> requestBodies) {
        Inventory inventory = super.findByUuid(UUID.fromString(Optional.ofNullable(uuid).orElse(uuidParam)));
        List<Item> items = Optional.ofNullable(requestBodies).orElse(new ArrayList<>());
        if(uuidParam != null) {
            Item paramItem = new Item();
            paramItem.setUuid(UUID.fromString(uuidParam));
            items.add(paramItem);
        }
        for(Item item: items) {
            InventoryUtils.consumeOwnedItemByItemUuid(itemController
                            .findByUuid(item.getUuid()), inventory,
                    Optional.ofNullable(amount).orElse(1));
        }
        return super.save(inventory);
    }

    @GetMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE + ServiceStrings.ITEM_PATH)
    public OwnedItems getOwnedItemsByInventoryUuid(
            @PathVariable(value = ServiceStrings.UUID, required = false) String uuid,
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Inventory requestBody) {
        return super.findByUuid(
                getUuidFromBodyOrParam(requestBody,
                        Optional.ofNullable(uuid).orElse(uuidParam))).getOwnedItems();
    }

}
