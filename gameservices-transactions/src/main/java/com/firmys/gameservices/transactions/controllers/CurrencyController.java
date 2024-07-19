package com.firmys.gameservices.transactions.controllers;

import static com.firmys.gameservices.common.ServiceConstants.CURRENCIES_PATH;
import static com.firmys.gameservices.common.ServiceConstants.CURRENCY_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.transactions.models.Currency;
import com.firmys.gameservices.transactions.services.CurrencyService;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@RestController
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CurrencyController extends CommonController<Currency> {

  private final CurrencyService service;
  private final Class<Currency> entityClass = Currency.class;

  @GetMapping(CURRENCIES_PATH)
  public Flux<Currency> batchGet(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.find(uuids);
  }

  @GetMapping(CURRENCY_PATH)
  public Mono<Currency> get(
      @RequestParam(value = ServiceConstants.UUID, required = false) UUID uuid) {
    return service.find(uuid);
  }

  @PostMapping(value = CURRENCIES_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Currency> batchCreate(@RequestBody Flux<Currency> objects) {
    return service.create(objects);
  }

  @PostMapping(value = CURRENCY_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Currency> create(@RequestBody Mono<Currency> object) {
    return service.create(object);
  }

  @PutMapping(value = CURRENCIES_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Currency> batchUpdate(@RequestBody Flux<Currency> objects) {
    return service.update(objects);
  }

  @PutMapping(value = CURRENCY_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Currency> update(@RequestBody Mono<Currency> object) {
    return service.update(object);
  }

  @DeleteMapping(CURRENCIES_PATH)
  public Mono<Void> batchDelete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.delete(uuids);
  }

  @DeleteMapping(CURRENCY_PATH)
  public Mono<Void> delete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Mono<UUID> uuid) {
    return service.delete(uuid);
  }
}
