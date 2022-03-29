package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.GameData;
import com.firmys.gameservice.common.GameServiceError;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservice.inventory.service.currency.CurrencyService;
import com.firmys.gameservice.inventory.service.data.Currency;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class CurrencyController {
    public static final String baseDataName = ServiceConstants.CURRENCY;
    public static final String basePath = "/" + ServiceConstants.CURRENCY;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final CurrencyService service;
    private final CurrencyDataLookup dataLookup;

    public CurrencyController(
            CurrencyService service,
            CurrencyDataLookup dataLookup) {
        this.service = service;
        this.dataLookup = dataLookup;
    }

    @GetMapping(basePath)
    public Set<GameData> findAll() {
        return StreamSupport.stream(service.findAll(Sort.unsorted()).spliterator(), false)
                .collect(Collectors.toSet());
    }

    @PostMapping(basePath)
    public GameData save(@RequestBody Currency entity) {
        return service.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(@RequestBody Currency entity) {
        service.deleteById(dataLookup.getPrimaryKeyByUuid(entity.getUuid()));
    }

    @GetMapping(basePath + uuidPath)
    public Currency findByUuid(@PathVariable(ServiceConstants.UUID) String uuidString) {
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
    public GameData update(@RequestBody Currency entity) {
        Currency existing = findByUuid(entity.getUuid().toString());
        existing.update(entity);
        save(existing);
        return dataLookup.getDataByUuid(entity.getUuid());
    }

    @PutMapping(basePath + uuidPath)
    public GameData updateByUuid(@PathVariable(ServiceConstants.UUID) String uuidString, @RequestBody Currency entity) {
        Currency existing = findByUuid(uuidString);
        existing.update(entity);
        save(existing);
        return dataLookup.getDataByUuid(uuidString);
    }

}
