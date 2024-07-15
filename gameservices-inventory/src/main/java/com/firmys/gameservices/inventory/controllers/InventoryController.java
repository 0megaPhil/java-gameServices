package com.firmys.gameservices.inventory.controllers;

import static com.firmys.gameservices.common.ServiceConstants.INVENTORIES_PATH;
import static com.firmys.gameservices.common.ServiceConstants.INVENTORY_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.inventory.models.Inventory;
import com.firmys.gameservices.inventory.services.InventoryService;
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
public class InventoryController extends CommonController<Inventory> {

  private final InventoryService service;
  private final Class<Inventory> entityClass = Inventory.class;

  @GetMapping(INVENTORIES_PATH)
  public Flux<Inventory> batchGet(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.find(uuids);
  }

  @GetMapping(INVENTORY_PATH)
  public Mono<Inventory> get(
      @RequestParam(value = ServiceConstants.UUID, required = false) UUID uuid) {
    return service.find(uuid);
  }

  @PostMapping(value = INVENTORIES_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Inventory> batchCreate(@RequestBody Flux<Inventory> objects) {
    return service.create(objects);
  }

  @PostMapping(value = INVENTORY_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Inventory> create(@RequestBody Mono<Inventory> object) {
    return service.create(object);
  }

  @PutMapping(value = INVENTORIES_PATH, consumes = APPLICATION_JSON_VALUE)
  public Flux<Inventory> batchUpdate(@RequestBody Flux<Inventory> objects) {
    return service.update(objects);
  }

  @PutMapping(value = INVENTORY_PATH, consumes = APPLICATION_JSON_VALUE)
  public Mono<Inventory> update(@RequestBody Mono<Inventory> object) {
    return service.update(object);
  }

  @DeleteMapping(INVENTORIES_PATH)
  public Mono<Void> batchDelete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Flux<UUID> uuids) {
    return service.delete(uuids);
  }

  @DeleteMapping(INVENTORY_PATH)
  public Mono<Void> delete(
      @RequestParam(value = ServiceConstants.UUID, required = false) Mono<UUID> uuid) {
    return service.delete(uuid);
  }
}
