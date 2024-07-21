package com.firmys.gameservices.inventory.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.inventory.models.Item;
import com.firmys.gameservices.inventory.services.ItemService;
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
public class ItemController extends CommonController<Item> {

  private final ItemService service;
  private final Class<Item> entityClass = Item.class;

  @GetMapping(ITEM_PATH + PATH_UUID)
  public Mono<Item> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(ITEM_PATH)
  public Flux<Item> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(value = CommonConstants.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Item> create(@RequestBody Item object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = CommonConstants.ITEM_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Item> update(@RequestBody Item object) {
    return service.update(object);
  }

  @DeleteMapping(CommonConstants.ITEM_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
