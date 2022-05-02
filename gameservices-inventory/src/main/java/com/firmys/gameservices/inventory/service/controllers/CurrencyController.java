package com.firmys.gameservices.inventory.service.controllers;

import com.firmys.gameservices.inventory.service.currency.CurrencyDataLookup;
import com.firmys.gameservices.inventory.service.currency.CurrencyService;
import com.firmys.gameservices.inventory.service.data.*;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
public class CurrencyController extends AbstractController<Currency> {

    public CurrencyController(
            CurrencyService service,
            CurrencyDataLookup dataLookup,
            EntityManager entityManager) {
        super(service, dataLookup, Currency.class, Currency::new, QCurrency.currency, entityManager);
    }

    /**
     * {@link ServiceConstants#CURRENCIES_PATH}
     *
     */
    @GetMapping(ServiceConstants.CURRENCIES_PATH)
    public Set<Currency> findSet(
            @RequestParam(value = ServiceConstants.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.findAll(uuidParams);
    }

    @PostMapping(value = ServiceConstants.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> createSet(
            @RequestBody Set<Currency> entities) {
        return super.save(entities);
    }

    @DeleteMapping(value = ServiceConstants.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceConstants.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceConstants.CURRENCIES_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Currency> updateSet(
            @RequestBody Set<Currency> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceConstants#CURRENCY_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServiceConstants.CURRENCY_PATH + ServiceConstants.UUID_PATH_VARIABLE)
    public Currency find(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceConstants.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency create(@RequestBody(required = false) Currency entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceConstants.CURRENCY_PATH + ServiceConstants.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceConstants.CURRENCY_PATH + ServiceConstants.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Currency update(@PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar,
                            @RequestBody Currency entity) {
        return super.update(uuidPathVar, entity);
    }

    /**
     * {@link ServiceConstants#CURRENCIES_PATH}/{@link ServiceConstants#QUERY_PATH}
     * @param queryMap key value style attributes such as http://url:port/path?key0=val0&key1=val1
     * @return set of entities which match attribute key and value restrictions
     */
    @GetMapping(value = ServiceConstants.CURRENCIES_PATH + ServiceConstants.QUERY_PATH)
    public Set<Currency> findAll(
            @RequestParam Map<String, String> queryMap) {
        return super.findAll(queryMap);
    }

}
