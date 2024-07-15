package com.firmys.gameservices.inventory.controllers;

import static com.firmys.gameservices.common.ServiceConstants.ITEMS_PATH;
import static com.firmys.gameservices.common.ServiceConstants.ITEM_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.inventory.models.Item;
import com.firmys.gameservices.inventory.services.ItemService;
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
public class ItemController extends CommonController<Item> {

  private final ItemService service;
  private final Class<Item> entityClass = Item.class;

  @GetMapping(ITEMS_PATH)
  public Flux<Item> batchGet(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.find(uuids);
  }

  @GetMapping(ITEM_PATH)
  public Mono<Item> get(@RequestParam(value = ServiceConstants.UUID, required = false) UUID uuid) {
    return service.find(uuid);
  }

  @PostMapping(value = ITEMS_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Item> batchCreate(@RequestBody Flux<Item> objects) {
    return service.create(objects);
  }

  @PostMapping(value = ITEM_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Item> create(@RequestBody Mono<Item> object) {
    return service.create(object);
  }

  @PutMapping(value = ITEMS_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Item> batchUpdate(@RequestBody Flux<Item> objects) {
    return service.update(objects);
  }

  @PutMapping(value = ITEM_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Item> update(@RequestBody Mono<Item> object) {
    return service.update(object);
  }

  @DeleteMapping(ITEMS_PATH)
  public Mono<Void> batchDelete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.delete(uuids);
  }

  @DeleteMapping(ITEM_PATH)
  public Mono<Void> delete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Mono<UUID> uuid) {
    return service.delete(uuid);
  }
}
