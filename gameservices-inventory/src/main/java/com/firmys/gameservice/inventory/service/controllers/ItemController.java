package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.data.Item;
import com.firmys.gameservice.inventory.service.item.ItemDataLookup;
import com.firmys.gameservice.inventory.service.item.ItemService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class ItemController extends AbstractController<Item> {
    public static final String basePath = "/" + ServiceConstants.ITEM;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final ItemService service;
    private final ItemDataLookup dataLookup;

    public ItemController(
            ItemService service,
            ItemDataLookup dataLookup) {
        super(service, dataLookup, Item.class, Item::new);
        this.service = service;
        this.dataLookup = dataLookup;
    }

    @GetMapping(basePath)
    public Set<Item> findAll() {
        return super.findAll();
    }

    @PostMapping(basePath)
    public Item save(@RequestBody(required = false) Item entity) {
        return super.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Item entityBody) {
        super.delete(uuidParam, entityBody);
    }

    @GetMapping(basePath + uuidPath)
    public Item findByUuid(@PathVariable(ServiceConstants.UUID) String pathUuid) {
        return super.findByUuid(pathUuid);
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(ServiceConstants.UUID) String pathUuid) {
        super.deleteByUuid(pathUuid);
    }

    @PutMapping(basePath)
    public Item update(@RequestBody Item entity) {
        return super.update(entity);
    }

    @PutMapping(basePath + uuidPath)
    public Item updateByUuid(@PathVariable(ServiceConstants.UUID) String pathUuid,
                             @RequestBody Item entity) {
        return super.updateByUuid(pathUuid, entity);
    }

}
