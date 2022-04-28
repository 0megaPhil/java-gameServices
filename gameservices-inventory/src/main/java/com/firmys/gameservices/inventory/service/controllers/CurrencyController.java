package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservices.inventory.service.currency.CurrencyService;
import com.firmys.gameservices.inventory.service.data.*;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class CurrencyController extends AbstractController<Currency> {

    public CurrencyController(
            CurrencyService service,
            CurrencyDataLookup dataLookup,
            EntityManager entityManager) {
        super(service, dataLookup, Currency.class, Currency::new, QCurrency.currency, entityManager);
    }

    /**
     * {@link ServiceStrings#CURRENCIES_PATH}
     *
     */
    @GetMapping(ServiceStrings.CURRENCIES_PATH)
    public Set<Currency> findSet(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.find(uuidParams);
    }

    @PostMapping(value = ServiceStrings.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> createSet(
            @RequestBody Set<Currency> currencies) {
        return currencies.stream().map(super::save).collect(Collectors.toSet());
    }

    @DeleteMapping(value = ServiceStrings.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceStrings.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceStrings.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> updateSet(
            @RequestBody Set<Currency> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#CURRENCY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServiceStrings.CURRENCY_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Currency find(
            @PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceStrings.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency create(@RequestBody(required = false) Currency entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.CURRENCY_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceStrings.CURRENCY_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency update(@PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar,
                            @RequestBody Currency entity) {
        return super.update(uuidPathVar, entity);
    }

    /**
     * {@link ServiceStrings#CURRENCIES_PATH}/{@link ServiceStrings#QUERY_PATH}
     * @param queryMap key value style attributes such as http://url:port/path?key0=val0&key1=val1
     * @return set of entities which match attribute key and value restrictions
     */
    @GetMapping(value = ServiceStrings.CURRENCIES_PATH + ServiceStrings.QUERY_PATH)
    public Set<Currency> findAll(
            @RequestParam Map<String, String> queryMap) {
        return super.findAll(queryMap);
    }

}
