package com.firmys.gameservices.transactions.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Currency;
import com.firmys.gameservices.transactions.services.CurrencyService;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@RestController
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CurrencyController extends CommonController<Currency> {

  private final CurrencyService service;
  private final Class<Currency> entityClass = Currency.class;

  @GetMapping(CURRENCY_PATH + PATH_UUID)
  public Mono<Currency> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(CURRENCY_PATH)
  public Flux<Currency> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(value = CommonConstants.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Currency> create(@RequestBody Currency object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = CommonConstants.CURRENCY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Currency> update(@RequestBody Currency object) {
    return service.update(object);
  }

  @DeleteMapping(CommonConstants.CURRENCY_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
