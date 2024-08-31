package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.AttributeRepository;
import com.firmys.gameservices.generated.models.Attribute;
import com.firmys.gameservices.generated.models.AttributeEntry;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class AttributeService extends GameService<Attribute> {
  private final AttributeRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Attribute> entityType = Attribute.class;

  @Override
  public Mono<Attribute> create(Attribute object) {
    return create(Mono.just(object));
  }

  @Override
  public Mono<Attribute> create(Mono<Attribute> object) {
    return super.create(object);
  }

  public Function<AttributeEntry, Mono<AttributeEntry>> resolveEntry() {
    return entry ->
        findAllLike(entry.attribute())
            .take(1)
            .singleOrEmpty()
            .switchIfEmpty(create(entry.attribute()))
            .map(
                attr ->
                    entry
                        .withContext(attributeContext(entry.value(), entry.attribute()))
                        .withAttribute(attr));
  }
}
