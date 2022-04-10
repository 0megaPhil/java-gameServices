package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservice.inventory.service.currency.CurrencyService;
import com.firmys.gameservice.inventory.service.data.Currency;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
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
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServicePaths.CURRENCIES_PATH)
    public Set<Currency> find(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    @PostMapping(value = ServicePaths.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> add(@RequestBody(required = false) Set<Currency> entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServicePaths.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServicePaths.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Currency> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServicePaths.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> update(
            @RequestBody Set<Currency> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServicePaths#CURRENCY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServicePaths.CURRENCY_PATH)
    public Currency findByUuidParam(
            @RequestParam(value = ServicePaths.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE)
    public Currency findByUuidPath(
            @PathVariable(ServicePaths.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServicePaths.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency add(@RequestBody(required = false) Currency entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServicePaths.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServicePaths.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Currency entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServicePaths.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServicePaths.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency update(@RequestBody Currency entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServicePaths.CURRENCY_PATH + ServicePaths.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency updateByUuid(@PathVariable(ServicePaths.UUID) String pathUuid,
                                 @RequestBody Currency entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

}
