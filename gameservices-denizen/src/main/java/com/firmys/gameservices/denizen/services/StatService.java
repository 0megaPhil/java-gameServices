package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.StatRepository;
import com.firmys.gameservices.generated.models.Stat;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class StatService extends GameService<Stat> {
  private final StatRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Stat> entityType = Stat.class;

  @Override
  public Function<Stat, Stat> prompt() {
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
                .append("', and the Stat 'value' range is from '1' to '20'")
                .append(", with '1' being the worst")
                .append(", '10' being average, and '20' being the best.")
                .append("\n</additional_instructions>")
                .toString());
  }
}
