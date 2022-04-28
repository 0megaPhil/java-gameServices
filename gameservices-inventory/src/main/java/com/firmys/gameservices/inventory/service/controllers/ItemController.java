package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.inventory.service.data.Item;
import com.firmys.gameservices.inventory.service.data.QItem;
import com.firmys.gameservices.inventory.service.item.ItemDataLookup;
import com.firmys.gameservices.inventory.service.item.ItemService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ItemController extends AbstractController<Item> {

    public ItemController(
            ItemService service,
            ItemDataLookup dataLookup,
            EntityManager entityManager) {
        super(service, dataLookup, Item.class, Item::new, QItem.item, entityManager);
    }

    /**
     * {@link ServiceStrings#ITEMS_PATH}
     */
    @GetMapping(ServiceStrings.ITEMS_PATH) // TODO - Use proper queries
    public Set<Item> findSet(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.find(uuidParams);
    }

    @PostMapping(value = ServiceStrings.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Item> createSet(
            @RequestBody Set<Item> currencies) {
        return currencies.stream().map(super::save).collect(Collectors.toSet());
    }

    @DeleteMapping(value = ServiceStrings.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceStrings.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceStrings.ITEMS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Item> updateSet(
            @RequestBody Set<Item> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#ITEM_PATH}
     */
    @GetMapping(value = ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Item find(
            @PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceStrings.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item create(@RequestBody(required = false) Item entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceStrings.ITEM_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Item update(@PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar,
                           @RequestBody Item entity) {
        return super.update(uuidPathVar, entity);
    }

    /**
     * {@link ServiceStrings#ITEMS_PATH}/{@link ServiceStrings#QUERY_PATH}
     * @param queryMap key value style attributes such as http://url:port/path?key0=val0&key1=val1
     * @return set of entities which match attribute key and value restrictions
     */
    @GetMapping(value = ServiceStrings.ITEMS_PATH + ServiceStrings.QUERY_PATH)
    public Set<Item> findAll(
            @RequestParam Map<String, String> queryMap) {
        return super.findAll(queryMap);
    }

}
