package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.inventory.service.data.Item;
import com.firmys.gameservices.inventory.service.item.ItemDataLookup;
import com.firmys.gameservices.inventory.service.item.ItemService;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ItemController extends AbstractController<Item> {

    public ItemController(
            ItemService service,
            ItemDataLookup dataLookup) {
        super(service, dataLookup, Item.class, Item::new);
    }

    /**
     * {@link ServiceStrings#ITEMS_PATH}
     * Multiple methods do not support UUID as path variable
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServiceStrings.ITEMS_PATH)
    public Set<Item> findMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    @PostMapping(value = ServiceStrings.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Item> addMultiple(@RequestBody(required = false) Set<Item> entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Item> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServiceStrings.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Item> updateMultiple(
            @RequestBody Set<Item> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#ITEM_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServiceStrings.ITEM_PATH)
    public Item findByUuidParam(
            @RequestParam(value = ServiceStrings.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Item findByUuidPath(
            @PathVariable(ServiceStrings.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServiceStrings.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item add(@RequestBody(required = false) Item entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Item entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServiceStrings.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item update(@RequestBody Item entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item updateByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid,
                                  @RequestBody Item entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

}
