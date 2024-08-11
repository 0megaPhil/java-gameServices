package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.data.EffectRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.generated.models.Effect;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class EffectService extends CommonService<Effect> {
  private final EffectRepository repository;
  private final GatewayClient gatewayClient;

  @Override
  public Mono<Effect> create(Mono<Effect> object) {
    return object
        .flatMap(this::findBy)
        .flatMap(
            obj ->
                Optional.of(obj.uuid())
                    .filter(id -> creatable().test(obj))
                    .map(id -> complete().apply(obj))
                    .map(o -> super.create(Mono.fromSupplier(() -> o)))
                    .orElse(Mono.fromSupplier(() -> obj)));
  }

  public Predicate<Effect> creatable() {
    return obj -> obj != null && obj.uuid() == null && obj.type() != null;
  }

  public Function<Effect, Effect> complete() {
    return obj -> prompt().apply(name().apply(obj));
  }

  private Function<Effect, Effect> prompt() {
    return obj ->
        Optional.of(obj)
            .filter(st -> st.prompt() != null)
            .orElseGet(
                () ->
                    obj.toBuilder()
                        .prompt(
                            "Describe "
                                + obj.getClass().getSimpleName()
                                + " with name "
                                + obj.name())
                        .build());
  }

  public Function<Effect, Effect> name() {
    return stat ->
        Optional.of(stat)
            .filter(st -> st.name() != null)
            .orElseGet(() -> stat.toBuilder().name(stat.type().name()).build());
  }
}
