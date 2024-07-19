package com.firmys.gameservices.transactions.controllers;

import static com.firmys.gameservices.common.ServiceConstants.TRANSACTIONS_PATH;
import static com.firmys.gameservices.common.ServiceConstants.TRANSACTION_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.transactions.models.Transaction;
import com.firmys.gameservices.transactions.services.TransactionService;
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
public class TransactionController extends CommonController<Transaction> {

  private final TransactionService service;
  private final Class<Transaction> entityClass = Transaction.class;

  @GetMapping(TRANSACTIONS_PATH)
  public Flux<Transaction> batchGet(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.find(uuids);
  }

  @GetMapping(TRANSACTION_PATH)
  public Mono<Transaction> get(
      @RequestParam(value = ServiceConstants.UUID, required = false) UUID uuid) {
    return service.find(uuid);
  }

  @PostMapping(value = TRANSACTIONS_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Transaction> batchCreate(@RequestBody Flux<Transaction> objects) {
    return service.create(objects);
  }

  @PostMapping(value = TRANSACTION_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Transaction> create(@RequestBody Mono<Transaction> object) {
    return service.create(object);
  }

  @PutMapping(value = TRANSACTIONS_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Transaction> batchUpdate(@RequestBody Flux<Transaction> objects) {
    return service.update(objects);
  }

  @PutMapping(value = TRANSACTION_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Transaction> update(@RequestBody Mono<Transaction> object) {
    return service.update(object);
  }

  @DeleteMapping(TRANSACTIONS_PATH)
  public Mono<Void> batchDelete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.delete(uuids);
  }

  @DeleteMapping(TRANSACTION_PATH)
  public Mono<Void> delete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Mono<UUID> uuid) {
    return service.delete(uuid);
  }
}
