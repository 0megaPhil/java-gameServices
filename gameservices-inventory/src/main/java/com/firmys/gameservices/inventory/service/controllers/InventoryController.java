package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
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
     * {@link ServiceStrings#INVENTORIES_PATH}
     */
    @GetMapping(ServiceStrings.INVENTORIES_PATH)
    public Set<Inventory> findSet(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.find(uuidParams);
    }

    /**
     * A bit of a special case, as Inventory have no identifying attributes, and so creating bulk is handled differently
     *
     * @param amount number of {@link Inventory} to create
     * @return set of newly generated Inventory objects
     */
    @PostMapping(value = ServiceStrings.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> createSet(
            @RequestParam(value = ServiceStrings.AMOUNT) int amount) {
        return IntStream.range(0, amount).mapToObj(i -> super.save(new Inventory())).collect(Collectors.toSet());
    }

    @DeleteMapping(value = ServiceStrings.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceStrings.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceStrings.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> updateSet(
            @RequestBody Set<Inventory> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#INVENTORY_PATH}
     */
    @GetMapping(value = ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Inventory find(
            @PathVariable(ServiceStrings.UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceStrings.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory create(@RequestBody(required = false) Inventory entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceStrings.UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory update(@PathVariable(ServiceStrings.UUID) UUID uuidPathVar,
                            @RequestBody Inventory entity) {
        return super.update(uuidPathVar, entity);
    }

    @PutMapping(
            ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE +
                    ServiceStrings.CURRENCY_PATH + ServiceStrings.CREDIT_PATH)
    public Inventory creditCurrency(
            @PathVariable(value = ServiceStrings.UUID) UUID uuid,
            @RequestParam(value = ServiceStrings.UUID) UUID currencyUuid,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amountParam) {
        return super.save(InventoryUtils.creditCurrency(
                currencyController.find(currencyUuid),
                super.find(uuid), amountParam));
    }

    @PutMapping(
            ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE +
                    ServiceStrings.CURRENCY_PATH + ServiceStrings.DEBIT_PATH)
    public Inventory debitCurrency(
            @PathVariable(value = ServiceStrings.UUID) UUID uuid,
            @RequestParam(value = ServiceStrings.UUID) UUID currencyUuid,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amountParam) {
        return super.save(InventoryUtils.debitCurrency(
                currencyController.find(currencyUuid),
                super.find(uuid), amountParam));
    }

    @PutMapping(ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE +
            ServiceStrings.ITEM_PATH + ServiceStrings.ADD_PATH)
    public Inventory addOwnedItem(
            @PathVariable(ServiceStrings.UUID) UUID uuid,
            @RequestParam(value = ServiceStrings.UUID) UUID uuidParam,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amount) {
        return super.save(InventoryUtils.addOwnedItemByItemUuid(itemController
                        .find(uuidParam),
                super.find(uuid), amount));
    }

    @PutMapping(
            ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE +
                    ServiceStrings.ITEMS_PATH + ServiceStrings.ADD_PATH)
    public Inventory addOwnedItems(
            @PathVariable(ServiceStrings.UUID) UUID uuid,
            @RequestParam(value = ServiceStrings.UUID) Set<UUID> uuidParams,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amount) {
        AtomicReference<Inventory> inventory = new AtomicReference<>(super.find(uuid));
        itemController.find(uuidParams).forEach(item ->
                inventory.set(InventoryUtils.addOwnedItemByItemUuid(item, inventory.get(), amount)));
        return super.save(inventory.get());
    }

    @PutMapping(
            ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE +
                    ServiceStrings.ITEM_PATH + ServiceStrings.CONSUME_PATH)
    public Inventory consumeOwnedItem(
            @PathVariable(ServiceStrings.UUID) UUID uuid,
            @RequestParam(value = ServiceStrings.UUID) UUID uuidParam,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amount) {
        return super.save(InventoryUtils.consumeOwnedItemByItem(itemController
                        .find(uuidParam),
                super.find(uuid), amount));
    }

    @PutMapping(
            ServiceStrings.INVENTORY_PATH + ServiceStrings.UUID_PATH_VARIABLE +
                    ServiceStrings.ITEMS_PATH + ServiceStrings.CONSUME_PATH)
    public Inventory consumeOwnedItems(
            @PathVariable(ServiceStrings.UUID) UUID uuid,
            @RequestParam(value = ServiceStrings.UUID) Set<UUID> uuidParams,
            @RequestParam(value = ServiceStrings.AMOUNT) Integer amount) {
        AtomicReference<Inventory> inventory = new AtomicReference<>(super.find(uuid));
        itemController.find(uuidParams).forEach(item ->
                inventory.set(InventoryUtils.consumeOwnedItemByItem(item, inventory.get(), amount)));
        return super.save(inventory.get());
    }

}
