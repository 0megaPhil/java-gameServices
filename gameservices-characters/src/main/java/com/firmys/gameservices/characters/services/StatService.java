package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.data.StatRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.generated.models.Stat;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class StatService extends CommonService<Stat> {
  private final StatRepository repository;
  private final GatewayClient gatewayClient;

  @Override
  public Mono<Stat> create(Mono<Stat> object) {
    return object
        .doOnError(th -> log.error(th.getMessage(), th))
        .flatMap(this::findBy)
        .flatMap(
            obj ->
                Optional.ofNullable(obj)
                    .filter(o -> creatable().test(o))
                    .map(o -> complete().apply(o))
                    .map(o -> super.create(Mono.fromSupplier(() -> o)))
                    .orElse(Mono.fromSupplier(() -> obj)));
  }

  public Predicate<Stat> creatable() {
    return obj -> obj != null && obj.uuid() == null && obj.type() != null;
  }

  public Function<Stat, Stat> complete() {
    return obj -> prompt().apply(name().apply(obj));
  }

  private Function<Stat, Stat> prompt() {
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

  public Function<Stat, Stat> name() {
    return stat ->
        Optional.of(stat)
            .filter(st -> st.name() != null)
            .orElseGet(() -> stat.toBuilder().name(stat.type().name()).build());
  }
}
