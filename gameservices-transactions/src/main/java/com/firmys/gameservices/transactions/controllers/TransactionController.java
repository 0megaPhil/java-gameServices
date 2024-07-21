package com.firmys.gameservices.transactions.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.transactions.models.Transaction;
import com.firmys.gameservices.transactions.services.TransactionService;
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
public class TransactionController extends CommonController<Transaction> {

  private final TransactionService service;
  private final Class<Transaction> entityClass = Transaction.class;

  @GetMapping(TRANSACTION_PATH + PATH_UUID)
  public Mono<Transaction> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(TRANSACTION_PATH)
  public Flux<Transaction> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(
      value = CommonConstants.TRANSACTION_PATH,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Transaction> create(@RequestBody Transaction object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = CommonConstants.TRANSACTION_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Transaction> update(@RequestBody Transaction object) {
    return service.update(object);
  }

  @DeleteMapping(CommonConstants.TRANSACTION_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
