package com.firmys.gameservices.inventory.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Inventory;
import com.firmys.gameservices.inventory.services.InventoryService;
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
public class InventoryController extends CommonController<Inventory> {

  private final InventoryService service;
  private final Class<Inventory> entityClass = Inventory.class;

  @GetMapping(INVENTORY_PATH + PATH_UUID)
  public Mono<Inventory> get(@PathVariable UUID uuid) {
    return service.find(uuid);
  }

  @GetMapping(INVENTORY_PATH)
  public Flux<Inventory> get(@RequestParam(value = LIMIT, required = false) Integer limit) {
    return service.find(limit);
  }

  @PostMapping(value = CommonConstants.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Inventory> create(@RequestBody Inventory object) {
    return service.create(Mono.just(object));
  }

  @PutMapping(value = CommonConstants.INVENTORY_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Inventory> update(@RequestBody Inventory object) {
    return service.update(object);
  }

  @DeleteMapping(CommonConstants.INVENTORY_PATH)
  public Mono<Void> delete(
      @RequestParam(value = CommonConstants.UUID, required = false) UUID uuid) {
    return service.delete(uuid);
  }
}
