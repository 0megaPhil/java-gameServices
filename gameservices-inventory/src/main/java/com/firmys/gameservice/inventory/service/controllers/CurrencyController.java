package com.firmys.gameservice.inventory.service.controllers;

import com.firmys.gameservice.common.*;
import com.firmys.gameservice.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservice.inventory.service.currency.CurrencyService;
import com.firmys.gameservice.inventory.service.data.Currency;
import com.firmys.gameservice.inventory.service.data.Item;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class CurrencyController extends AbstractController<Currency> {
    public static final String basePath = "/" + ServiceConstants.CURRENCY;
    public static final String uuidPath = "/" + "{" + ServiceConstants.UUID + "}";

    private final CurrencyService service;
    private final CurrencyDataLookup dataLookup;

    public CurrencyController(
            CurrencyService service,
            CurrencyDataLookup dataLookup) {
        super(service, dataLookup, Currency.class, Currency::new);
        this.service = service;
        this.dataLookup = dataLookup;
    }

    @GetMapping(basePath)
    public Set<Currency> findAll() {
        return super.findAll();
    }

    @PostMapping(basePath)
    public Currency save(@RequestBody(required = false) Currency entity) {
        return super.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Currency entityBody) {
        super.delete(uuidParam, entityBody);
    }

    @GetMapping(basePath + uuidPath)
    public Currency findByUuid(@PathVariable(ServiceConstants.UUID) String pathUuid) {
        return super.findByUuid(pathUuid);
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(ServiceConstants.UUID) String pathUuid) {
        super.deleteByUuid(pathUuid);
    }

    @PutMapping(basePath)
    public Currency update(@RequestBody Currency entity) {
        return super.update(entity);
    }

    @PutMapping(basePath + uuidPath)
    public Currency updateByUuid(@PathVariable(ServiceConstants.UUID) String pathUuid,
                             @RequestBody Currency entity) {
        return super.updateByUuid(pathUuid, entity);
    }

}
