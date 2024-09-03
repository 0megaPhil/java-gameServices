package com.firmys.gameservices.inventory.services;

import static com.firmys.gameservices.common.FunctionUtils.safeSet;

import com.firmys.gameservices.generated.models.Flavors;
import com.firmys.gameservices.generated.models.Inventory;
import com.firmys.gameservices.inventory.repositories.InventoryRepository;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import java.util.HashSet;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class InventoryService extends GameService<Inventory> {
  private final InventoryRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Inventory> entityType = Inventory.class;

  @Override
  public Mono<Inventory> create(Mono<Inventory> object) {
    return super.create(
        object.map(
            obj -> obj.withCurrencies(safeSet(obj.currencies())).withItems(safeSet(obj.items()))));
  }

  @Override
  public Inventory prompts(Inventory entity, Flavors type) {
    return entity.withEntries(new HashSet<>());
  }
}
