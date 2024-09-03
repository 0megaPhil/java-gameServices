package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.AttributeRepository;
import com.firmys.gameservices.generated.models.Attribute;
import com.firmys.gameservices.generated.models.AttributeEntry;
import com.firmys.gameservices.generated.models.Flavors;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import java.text.MessageFormat;
import java.util.Optional;
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

  public Attribute prompts(Attribute entity, Flavors type) {
    return entity.withPrompt(attributePrompt(entity, type));
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

  public String attributePrompt(Attribute attribute, Flavors type) {
    return MessageFormat.format(
        "Create an interesting description for {0}, "
            + "with name of {1}. \n"
            + "{2}"
            + "{3}"
            + "{4}"
            + "{5}"
            + "{6}"
            + "{7}"
            + "{8}",
        Optional.ofNullable(type)
            .map(en -> "Attribute of type " + en.name())
            .orElse(entityType().getSimpleName()),
        attribute.name(),
        Optional.ofNullable(attribute.lowest())
            .map(txt -> txt + " is the lowest possible value. \n")
            .orElse(""),
        Optional.ofNullable(attribute.lower())
            .map(txt -> txt + " is considered a lower than average value. \n")
            .orElse(""),
        Optional.ofNullable(attribute.low())
            .map(txt -> txt + " is considered a somewhat lower than average value. \n")
            .orElse(""),
        Optional.ofNullable(attribute.average())
            .map(txt -> txt + " is considered an average value. \n")
            .orElse(""),
        Optional.ofNullable(attribute.high())
            .map(txt -> txt + " is considered a somewhat higher than average value. \n")
            .orElse(""),
        Optional.ofNullable(attribute.higher())
            .map(txt -> txt + " is considered a higher than average value. \n")
            .orElse(""),
        Optional.ofNullable(attribute.highest())
            .map(txt -> txt + " is considered the highest possible value. \n")
            .orElse(""));
  }
}
