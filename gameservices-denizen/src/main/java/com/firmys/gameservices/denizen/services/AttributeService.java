package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.AttributeRepository;
import com.firmys.gameservices.generated.models.Attribute;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class AttributeService extends GameService<Attribute> {
  private final AttributeRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Attribute> entityType = Attribute.class;

  @Override
  public Function<Attribute, Attribute> prompt() {
    return object ->
        object.withPrompt(
            promptBuilder()
                .append("<additional_instructions>\n")
                .append("The ")
                .append(entityType.getSimpleName())
                .append(" type is ")
                .append(object.type())
                .append("and the ")
                .append(entityType.getSimpleName())
                .append(" name is ")
                .append(object.name())
                .append("\n</additional_instructions>")
                .toString());
  }
}
