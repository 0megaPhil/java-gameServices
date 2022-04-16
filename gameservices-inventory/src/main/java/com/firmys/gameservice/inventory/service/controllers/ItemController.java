package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.item.ItemDataLookup;
import com.firmys.gameservice.inventory.service.item.ItemService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
     * {@link ServicePaths#ITEMS_PATH}
     * Multiple methods do not support UUID as path variable
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServicePaths.ITEMS_PATH)
    public Set<Item> find(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    @PostMapping(value = ServicePaths.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Item> add(@RequestBody(required = false) Set<Item> entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServicePaths.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Item> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServicePaths.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Item> update(
            @RequestBody Set<Item> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServicePaths#ITEM_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServicePaths.ITEM_PATH)
    public Item findByUuidParam(
            @RequestParam(value = ServicePaths.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Item findByUuidPath(
            @PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServicePaths.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item add(@RequestBody(required = false) Item entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServicePaths.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Item entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServicePaths.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item update(@RequestBody Item entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServicePaths.ITEM_PATH + ServicePaths.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item updateByUuid(@PathVariable(ServicePaths.UUID) String pathUuid,
                                  @RequestBody Item entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

}
