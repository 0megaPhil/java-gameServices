package com.firmys.gameservices.denizen.services;

import static com.firmys.gameservices.common.FunctionUtils.safeSet;

import com.firmys.gameservices.denizen.data.DenizenRepository;
import com.firmys.gameservices.generated.models.Denizen;
import com.firmys.gameservices.generated.models.Inventory;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class DenizenService extends GameService<Denizen> {
  private final GameServiceClient gameServiceClient;
  private final DenizenRepository repository;

  private final AttributeService attributeService;
  private final ProfessionService professionService;

  private final Class<Denizen> entityType = Denizen.class;

  @Override
  public Function<Denizen, Denizen> prompt() {
    return super.prompt();
  }

  @Transactional
  public Mono<Denizen> create(Denizen object) {
    return create(Mono.just(object));
  }

  public Mono<Denizen> ensureValues(Mono<Denizen> object) {
    return object
        .flatMap(obj -> resolveAttributes().apply(obj))
        .flatMap(obj -> resolveInventory().apply(obj));
  }

  public Function<Denizen, Mono<Denizen>> resolveAttributes() {
    return denizen ->
        Flux.fromIterable(denizen.entries())
            .flatMap(entry -> attributeService.resolveEntry().apply(entry))
            .collectList()
            .map(entries -> denizen.withEntries(safeSet(entries)));
  }

  private Function<Denizen, Mono<Denizen>> resolveInventory() {
    return object ->
        Optional.of(object)
            .filter(obj -> obj.inventory() != null)
            .filter(obj -> obj.inventory().id() != null)
            .map(Mono::just)
            .orElseGet(
                () ->
                    gameServiceClient
                        .create(Inventory.builder().build())
                        .map(object::withInventory));
  }
}
