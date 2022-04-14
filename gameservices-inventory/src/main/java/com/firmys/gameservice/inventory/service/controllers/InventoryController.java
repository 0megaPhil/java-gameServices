package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.AbstractController;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServicePaths;
import com.firmys.gameservice.inventory.service.data.Currency;
import com.firmys.gameservice.inventory.service.data.Inventory;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.data.OwnedItem;
import com.firmys.gameservice.inventory.service.inventory.InventoryDataLookup;
import com.firmys.gameservice.inventory.service.inventory.InventoryService;
import com.firmys.gameservice.inventory.service.inventory.InventoryUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
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
     * {@link ServicePaths#INVENTORIES_PATH}
     * Multiple methods do not support UUID as path variable
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServicePaths.INVENTORIES_PATH)
    public Set<Inventory> find(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    @PostMapping(value = ServicePaths.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> add(@RequestBody(required = false) Set<Inventory> entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServicePaths.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Inventory> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServicePaths.INVENTORIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Inventory> update(
            @RequestBody Set<Inventory> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServicePaths#INVENTORY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServicePaths.INVENTORY_PATH)
    public Inventory findByUuidParam(
            @RequestParam(value = ServicePaths.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Inventory findByUuidPath(
            @PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServicePaths.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory add(@RequestBody(required = false) Inventory entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServicePaths.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Inventory entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServicePaths.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory update(@RequestBody Inventory entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Inventory updateByUuid(@PathVariable(ServicePaths.UUID) String pathUuid,
                                  @RequestBody Inventory entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

    /**
     * Complex control methods
     * Singular Inventory
     */
    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.CURRENCY_PATH)
    public Inventory creditCurrencyByUuid(
            @RequestParam(value = ServicePaths.UUID, required = false) String currencyUuid,
            @RequestParam(value = ServicePaths.AMOUNT) Integer amountParam,
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.creditCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid)),
                super.findByUuid(UUID.fromString(uuid)), amountParam));
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.CURRENCY_PATH)
    public Inventory debitCurrencyByUuid(
            @RequestParam(value = ServicePaths.UUID, required = false) String currencyUuid,
            @RequestParam(value = ServicePaths.AMOUNT) Integer amountParam,
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.debitCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid)),
                super.findByUuid(UUID.fromString(uuid)),
                amountParam));
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Set<Inventory> findInventoriesWithItemByUuidPath(
            @PathVariable(value = ServicePaths.UUID, required = false) String uuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(UUID.fromString(uuid)));
    }

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.ITEM_PATH)
    public Set<Inventory> findInventoriesWithItemByUuidParam(
            @RequestParam(value = ServicePaths.UUID, required = false) String itemUuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(UUID.fromString(itemUuid)));
    }


    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEM_PATH)
    public Inventory addOwnedItem(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.addOwnedItemByItemUuid(itemController
                        .findByUuid(getUuidFromBodyOrParam(requestBody, uuidParam)),
                super.findByUuid(UUID.fromString(uuid)), Optional.ofNullable(amount).orElse(1)));
    }

    @PutMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEMS_PATH)
    public Inventory addOwnedItems(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) List<Item> requestBodies) {
        Inventory inventory = super.findByUuid(UUID.fromString(Optional.ofNullable(uuid).orElse(uuidParam)));
        List<Item> items = Optional.ofNullable(requestBodies).orElse(new ArrayList<>());
        if(uuidParam != null) {
            Item paramItem = new Item();
            paramItem.setUuid(UUID.fromString(uuidParam));
            items.add(paramItem);
        }
        for(Item item: items) {
            InventoryUtils.addOwnedItemByItemUuid(itemController
                            .findByUuid(item.getUuid()), inventory,
                    Optional.ofNullable(amount).orElse(1));
        }
        return super.save(inventory);
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEM_PATH)
    public Inventory consumeOwnedItem(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.consumeOwnedItemByItemUuid(
                itemController.findByUuid(getUuidFromBodyOrParam(
                        requestBody, uuidParam)), super.findByUuid(UUID.fromString(uuid)),
                Optional.ofNullable(amount).orElse(1)));
    }

    @DeleteMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEMS_PATH)
    public Inventory consumeOwnedItems(
            @PathVariable(ServicePaths.UUID) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestParam(value = ServicePaths.AMOUNT, required = false) Integer amount,
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

    @GetMapping(ServicePaths.INVENTORY_PATH + ServicePaths.UUID_PATH_VARIABLE + ServicePaths.ITEM_PATH)
    public Set<OwnedItem> getOwnedItemsByInventoryUuid(
            @PathVariable(value = ServicePaths.UUID, required = false) String uuid,
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Inventory requestBody) {
        return super.findByUuid(
                getUuidFromBodyOrParam(requestBody,
                        Optional.ofNullable(uuid).orElse(uuidParam))).getOwnedItems();
    }

}
