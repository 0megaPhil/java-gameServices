package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.AbstractController;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.inventory.service.data.Currency;
import com.firmys.gameservice.inventory.service.data.Inventory;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.inventory.InventoryDataLookup;
import com.firmys.gameservice.inventory.service.inventory.InventoryService;
import com.firmys.gameservice.inventory.service.inventory.InventoryUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class InventoryController extends AbstractController<Inventory> {
    public static final String basePath = "/" + ServiceConstants.INVENTORY;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final InventoryService inventoryService;
    private final InventoryDataLookup dataLookup;
    private final ItemController itemController;
    private final CurrencyController currencyController;

    public InventoryController(
            InventoryService inventoryService,
            InventoryDataLookup dataLookup,
            ItemController itemController,
            CurrencyController currencyController) {
        super(inventoryService, dataLookup, Inventory.class, Inventory::new);
        this.inventoryService = inventoryService;
        this.dataLookup = dataLookup;
        this.itemController = itemController;
        this.currencyController = currencyController;
    }

    /*
     Standard Controller methods
     */
    @GetMapping(value = basePath)
    public Set<Inventory> findAll() {
        return super.findAll();
    }

    @PostMapping(basePath)
    public Inventory save(@RequestBody(required = false) Inventory entity) {
        return super.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Inventory entityBody) {
        super.delete(uuidParam, entityBody);
    }

    @GetMapping(basePath + uuidPath)
    public Inventory findByUuid(@PathVariable(value = ServiceConstants.UUID) String pathUuid) {
        return super.findByUuid(pathUuid);
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(uuidPath) String pathUuid) {
        super.deleteByUuid(pathUuid);
    }

    @PutMapping(basePath)
    public Inventory update(@RequestBody Inventory entity) {
        return super.update(entity);
    }

    @PutMapping(basePath + uuidPath)
    public Inventory updateByUuid(@PathVariable(uuidPath) String pathUuid,
                                  @RequestBody Inventory entity) {
        return super.updateByUuid(pathUuid, entity);
    }

    /*
     Non-standard controllers methods
     */
    @PutMapping(basePath + uuidPath + CurrencyController.basePath)
    public Inventory creditCurrencyByUuid(
            @RequestParam(value = "uuid", required = false) String currencyUuid,
            @RequestParam(value = "amount") Integer amountParam,
            @PathVariable(ServiceConstants.UUID) String entityUuid,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.creditCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid).toString()),
                super.findByUuid(entityUuid), amountParam));
    }

    @DeleteMapping(basePath + uuidPath + CurrencyController.basePath)
    public Inventory debitCurrencyByUuid(
            @RequestParam(value = "uuid", required = false) String currencyUuid,
            @RequestParam(value = "amount") Integer amountParam,
            @PathVariable(ServiceConstants.UUID) String entityUuid,
            @RequestBody(required = false) Currency requestBody) {
        return super.save(InventoryUtils.debitCurrency(
                currencyController.findByUuid(
                        getUuidFromBodyOrParam(requestBody, currencyUuid).toString()),
                super.findByUuid(entityUuid),
                amountParam));
    }

    @GetMapping(basePath + ItemController.basePath + uuidPath)
    public Set<Inventory> findInventoriesWithItemByUuidPath(
            @PathVariable(value = ServiceConstants.UUID, required = false) String uuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(uuid));
    }

    @GetMapping(basePath + ItemController.basePath)
    public Set<Inventory> findInventoriesWithItemByUuidParam(
            @RequestParam(value = "uuid", required = false) String itemUuid) {
        return InventoryUtils
                .getInventoriesWithItem(super.findAll(), itemController
                        .findByUuid(itemUuid));
    }


    @PutMapping(basePath + uuidPath + ItemController.basePath)
    public Inventory addOwnedItem(
            @PathVariable(ServiceConstants.UUID) String uuid,
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.addOwnedItemByItemUuid(itemController
                        .findByUuid(getUuidFromBodyOrParam(requestBody, uuidParam).toString()),
                super.findByUuid(uuid)));
    }

    @DeleteMapping(basePath + uuidPath + ItemController.basePath)
    public Inventory consumeOwnedItem(
            @PathVariable(ServiceConstants.UUID) String uuid,
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Item requestBody) {
        return super.save(InventoryUtils.consumeOwnedItemByItemUuid(
                itemController.findByUuid(getUuidFromBodyOrParam(
                        requestBody, uuidParam).toString()), super.findByUuid(uuid)));
    }
}
