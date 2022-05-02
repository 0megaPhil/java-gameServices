package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.inventory.service.data.*;
import com.firmys.gameservices.inventory.service.inventory.InventoryDataLookup;
import com.firmys.gameservices.inventory.service.inventory.InventoryService;
import com.firmys.gameservices.inventory.service.inventory.InventoryUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
            CurrencyController currencyController,
            EntityManager entityManager) {
        super(inventoryService, dataLookup, Inventory.class, Inventory::new, QInventory.inventory, entityManager);
        this.itemController = itemController;
        this.currencyController = currencyController;
    }

    /**
     * {@link ServiceConstants#INVENTORIES_PATH}
     */
    @GetMapping(ServiceConstants.INVENTORIES_PATH)
    public Set<Inventory> findSet(
            @RequestParam(value = ServiceConstants.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.findAll(uuidParams);
    }

    /**
     * A bit of a special case, as Inventory have no identifying attributes, and so creating bulk is handled differently
     *
     * @param amount number of {@link Inventory} to create
     * @return set of newly generated Inventory objects
     */
    @PostMapping(value = ServiceConstants.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> createSet(
            @RequestParam(value = ServiceConstants.AMOUNT) int amount) {
        return super.save(IntStream.range(0, amount).mapToObj(i -> new Inventory()).collect(Collectors.toSet()));
    }

    @DeleteMapping(value = ServiceConstants.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceConstants.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceConstants.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> updateSet(
            @RequestBody Set<Inventory> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceConstants#INVENTORY_PATH}
     */
    @GetMapping(value = ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE)
    public Inventory find(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceConstants.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory create(@RequestBody(required = false) Inventory entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceConstants.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory update(@RequestBody Inventory entity) {
        return super.update(entity);
    }

    @PutMapping(
            ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE +
                    ServiceConstants.CURRENCY_PATH + ServiceConstants.CREDIT_PATH)
    public Inventory creditCurrency(
            @PathVariable(value = ServiceConstants.PATH_UUID) UUID uuidPathVar,
            @RequestParam(value = ServiceConstants.CURRENCY) UUID currencyUuid,
            @RequestParam(value = ServiceConstants.AMOUNT) Integer amountParam) {
        return super.save(InventoryUtils.creditCurrency(
                currencyController.find(currencyUuid),
                super.find(uuidPathVar), amountParam));
    }

    @PutMapping(
            ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE +
                    ServiceConstants.CURRENCY_PATH + ServiceConstants.DEBIT_PATH)
    public Inventory debitCurrency(
            @PathVariable(value = ServiceConstants.PATH_UUID) UUID uuidPathVar,
            @RequestParam(value = ServiceConstants.CURRENCY) UUID currencyUuid,
            @RequestParam(value = ServiceConstants.AMOUNT) Integer amountParam) {
        return super.save(InventoryUtils.debitCurrency(
                currencyController.find(currencyUuid),
                super.find(uuidPathVar), amountParam));
    }

    @PutMapping(ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE +
            ServiceConstants.ITEM_PATH + ServiceConstants.ADD_PATH)
    public Inventory addOwnedItem(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar,
            @RequestParam(value = ServiceConstants.ITEM) UUID itemUuid,
            @RequestParam(value = ServiceConstants.AMOUNT) Integer amount) {
        return super.save(InventoryUtils.addOwnedItemByItemUuid(itemController
                        .find(itemUuid),
                super.find(uuidPathVar), amount));
    }

    @PutMapping(
            ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE +
                    ServiceConstants.ITEMS_PATH + ServiceConstants.ADD_PATH)
    public Inventory addOwnedItems(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar,
            @RequestParam(value = ServiceConstants.ITEM) Set<UUID> itemUuids,
            @RequestParam(value = ServiceConstants.AMOUNT) Integer amount) {
        AtomicReference<Inventory> inventory = new AtomicReference<>(super.find(uuidPathVar));
        itemController.findAll(itemUuids).forEach(item ->
                inventory.set(InventoryUtils.addOwnedItemByItemUuid(item, inventory.get(), amount)));
        return super.save(inventory.get());
    }

    @PutMapping(
            ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE +
                    ServiceConstants.ITEM_PATH + ServiceConstants.CONSUME_PATH)
    public Inventory consumeOwnedItem(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar,
            @RequestParam(value = ServiceConstants.ITEM) UUID itemUuid,
            @RequestParam(value = ServiceConstants.AMOUNT) Integer amount) {
        return super.save(InventoryUtils.consumeOwnedItemByItem(itemController
                        .find(itemUuid),
                super.find(uuidPathVar), amount));
    }

    @PutMapping(
            ServiceConstants.INVENTORY_PATH + ServiceConstants.UUID_PATH_VARIABLE +
                    ServiceConstants.ITEMS_PATH + ServiceConstants.CONSUME_PATH)
    public Inventory consumeOwnedItems(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar,
            @RequestParam(value = ServiceConstants.ITEM) Set<UUID> itemUuids,
            @RequestParam(value = ServiceConstants.AMOUNT) Integer amount) {
        AtomicReference<Inventory> inventory = new AtomicReference<>(super.find(uuidPathVar));
        itemController.findAll(itemUuids).forEach(item ->
                inventory.set(InventoryUtils.consumeOwnedItemByItem(item, inventory.get(), amount)));
        return super.save(inventory.get());
    }

}
