package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservice.inventory.service.currency.CurrencyService;
import com.firmys.gameservice.inventory.service.data.Currency;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class CurrencyController extends AbstractController<Currency> {

    public CurrencyController(
            CurrencyService service,
            CurrencyDataLookup dataLookup) {
        super(service, dataLookup, Currency.class, Currency::new);
    }

    /**
     * {@link ServicePaths#CURRENCIES_PATH}
     * Multiple methods do not support UUID as path variable
     *
     * Some methods, such as findMultiple, can collect UUIDs across parameters and body array
     */
    @GetMapping(ServicePaths.CURRENCIES_PATH)
    public Set<Currency> findAll() {
        return super.findAll();
    }

    @GetMapping(ServicePaths.CURRENCIES_PATH)
    public Set<Currency> findMultiple(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Currency> entities) {
        return super.findByUuids(gatherUuids(uuidParams, entities));
    }

    @PostMapping(ServicePaths.CURRENCIES_PATH)
    public Set<Currency> addMultiple(@RequestBody(required = false) Set<Currency> entity) {
        return entity.stream().map(super::save).collect(Collectors.toSet());
    }

    @DeleteMapping(ServicePaths.CURRENCIES_PATH)
    public void deleteMultiple(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Currency> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(ServicePaths.CURRENCIES_PATH)
    public Set<Currency> updateMultiple(
            @RequestBody Set<Currency> entities) {
        return entities.stream().map(e -> super.updateByUuid(e.getUuid(), e)).collect(Collectors.toSet());
    }

    /**
     * {@link ServicePaths#ITEM_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(ServicePaths.CURRENCY_PATH)
    public Currency findOne(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Currency entity) {
        return super.findByUuid(getUuidFromBodyOrParam(entity, uuidParam));
    }

    @GetMapping(ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Currency findOneByPath(@PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(ServicePaths.CURRENCY_PATH)
    public Currency addOne(@RequestBody(required = false) Currency entity) {
        return super.save(entity);
    }

    @DeleteMapping(ServicePaths.CURRENCY_PATH)
    public void deleteOne(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) Currency entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @GetMapping(ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Currency findOneByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @DeleteMapping(ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public void deleteOneByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(ServicePaths.CURRENCY_PATH)
    public Currency update(@RequestBody Currency entity) {
        return super.update(entity);
    }

    @PutMapping(ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Currency updateForUuid(@PathVariable(ServicePaths.UUID) String pathUuid,
                              @RequestBody Currency entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

}
