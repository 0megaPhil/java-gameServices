package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.GameData;
import com.firmys.gameservice.common.GameServiceError;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.item.ItemDataLookup;
import com.firmys.gameservice.inventory.service.item.ItemService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class ItemController {
    public static final String baseDataName = ServiceConstants.ITEM;
    public static final String basePath = "/" + ServiceConstants.ITEM;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final ItemService service;
    private final ItemDataLookup dataLookup;

    public ItemController(
            ItemService service,
            ItemDataLookup dataLookup) {
        this.service = service;
        this.dataLookup = dataLookup;
    }

    @GetMapping(basePath)
    public Set<Item> findAll() {
        return StreamSupport.stream(service.findAll(Sort.unsorted()).spliterator(), false)
                .collect(Collectors.toSet());
    }

    @PostMapping(basePath)
    public GameData save(@RequestBody Item entity) {
        return service.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(@RequestBody Item entity) {
        service.deleteById(dataLookup.getPrimaryKeyByUuid(entity.getUuid()));
    }

    @GetMapping(basePath + uuidPath)
    public Item findByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
        return service.findById(dataLookup.getPrimaryKeyByUuid(uuidString)).orElseThrow(
                () -> new RuntimeException(
                new GameServiceError(null, baseDataName + ": " + " not found by uuid",
                        "No matching record found", null).toString()));
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
        service.deleteById(dataLookup.getPrimaryKeyByUuid(uuidString));
    }

    @PutMapping(basePath)
    public Item update(@RequestBody Item entity) {
        Item existing = findByUuid(entity.getUuid().toString());
        existing.update(entity);
        save(existing);
        return dataLookup.getDataByUuid(entity.getUuid());
    }

    @PutMapping(basePath + uuidPath)
    public Item updateByUuid(@PathVariable(ServiceConstants.UUID) String uuidString, @RequestBody Item entity) {
        Item existing = findByUuid(uuidString);
        existing.update(entity);
        save(existing);
        return dataLookup.getDataByUuid(uuidString);
    }

}
