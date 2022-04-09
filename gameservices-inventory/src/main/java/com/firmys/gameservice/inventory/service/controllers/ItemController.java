package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.item.ItemDataLookup;
import com.firmys.gameservice.inventory.service.item.ItemService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class ItemController extends AbstractController<Item> {

    public ItemController(
            ItemService service,
            ItemDataLookup dataLookup) {
        super(service, dataLookup, Item.class, Item::new);
    }

    // TODO - fix to be like CharacterController
    /**
     * {@link ServicePaths#ITEMS_PATH}
     * Multiple methods do not support UUID as path variable
     *
     * Some methods, such as findMultiple, can collect UUIDs across parameters and body array
     */
    @GetMapping(ServicePaths.ITEMS_PATH)
    public Set<Item> findAll() {
        return super.findAll();
    }

    @GetMapping(ServicePaths.ITEMS_PATH)
    public Set<Item> findMultiple(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Item> entities) {
        return super.findByUuids(gatherUuids(uuidParams, entities));
    }

    @PostMapping(ServicePaths.ITEMS_PATH)
    public Set<Item> addMultiple(@RequestBody(required = false) Set<Item> entity) {
        return entity.stream().map(super::save).collect(Collectors.toSet());
    }

    @DeleteMapping(ServicePaths.ITEMS_PATH)
    public void deleteMultiple(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Item> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(ServicePaths.CURRENCIES_PATH)
    public Set<Item> updateMultiple(
            @RequestBody Set<Item> entities) {
        return entities.stream().map(e -> super.updateByUuid(e.getUuid(), e)).collect(Collectors.toSet());
    }

    /**
     * {@link ServicePaths#ITEM_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(ServicePaths.ITEM_PATH)
    public Item findOne(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Item entity) {
        return super.findByUuid(getUuidFromBodyOrParam(entity, uuidParam));
    }

    @GetMapping(ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Item findOneByPath(@PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(ServicePaths.ITEM_PATH)
    public Item addOne(@RequestBody(required = false) Item entity) {
        return super.save(entity);
    }

    @DeleteMapping(ServicePaths.ITEM_PATH)
    public void deleteOne(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Item entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @GetMapping(ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Item findOneByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @DeleteMapping(ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public void deleteOneByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(ServicePaths.ITEM_PATH)
    public Item update(@RequestBody Item entity) {
        return super.update(entity);
    }

    @PutMapping(ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Item updateForUuid(@PathVariable(ServicePaths.UUID) String pathUuid,
                                   @RequestBody Item entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

}
