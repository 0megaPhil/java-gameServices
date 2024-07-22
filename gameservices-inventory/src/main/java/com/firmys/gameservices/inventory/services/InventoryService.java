package com.firmys.gameservices.inventory.services;

import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.inventory.models.Inventory;
import com.firmys.gameservices.inventory.models.InventoryCurrency;
import com.firmys.gameservices.inventory.models.InventoryItem;
import com.firmys.gameservices.inventory.repositories.InventoryCurrencyRepository;
import com.firmys.gameservices.inventory.repositories.InventoryItemRepository;
import com.firmys.gameservices.inventory.repositories.InventoryRepository;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class InventoryService extends CommonService<Inventory> {
  private final InventoryRepository repository;
  private final InventoryItemRepository itemRepository;
  private final InventoryCurrencyRepository currencyRepository;

  @Override
  public Mono<Inventory> create(Mono<Inventory> object) {
    return super.create(object);
  }

  public Flux<InventoryItem> items(UUID inventoryId) {
    return find(inventoryId)
        .flatMapMany(inv -> itemRepository.findAllById(Flux.fromStream(inv.items().stream())));
  }

  public Flux<InventoryCurrency> currencies(UUID inventoryId) {
    return find(inventoryId)
        .flatMapMany(
            inv -> currencyRepository.findAllById(Flux.fromStream(inv.currencies().stream())));
  }

  public Flux<InventoryItem> items(Inventory inventory) {
    return itemRepository.findAllById(Flux.fromStream(inventory.items().stream()));
  }

  public Flux<InventoryCurrency> currencies(Inventory inventory) {
    return currencyRepository.findAllById(Flux.fromStream(inventory.currencies().stream()));
  }
}
