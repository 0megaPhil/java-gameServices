package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservices.inventory.service.currency.CurrencyService;
import com.firmys.gameservices.inventory.service.data.Currency;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CurrencyController extends AbstractController<Currency> {

    public CurrencyController(
            CurrencyService service,
            CurrencyDataLookup dataLookup) {
        super(service, dataLookup, Currency.class, Currency::new);
    }

    /**
     * {@link ServiceStrings#CURRENCIES_PATH}
     * Multiple methods do not support UUID as path variable
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServiceStrings.CURRENCIES_PATH)
    public Set<Currency> findMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    @PostMapping(value = ServiceStrings.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> addMultiple(@RequestBody(required = false) Set<Currency> entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Currency> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServiceStrings.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> updateMultiple(
            @RequestBody Set<Currency> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#CURRENCY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServiceStrings.CURRENCY_PATH)
    public Currency findByUuidParam(
            @RequestParam(value = ServiceStrings.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServiceStrings.CURRENCY_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Currency findByUuidPath(
            @PathVariable(ServiceStrings.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServiceStrings.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency add(@RequestBody(required = false) Currency entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Currency entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServiceStrings.CURRENCY_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServiceStrings.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency update(@RequestBody Currency entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServiceStrings.CURRENCY_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency updateByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid,
                                 @RequestBody Currency entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

}
