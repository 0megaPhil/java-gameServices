package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.AttributeRepository;
import com.firmys.gameservices.generated.models.Attribute;
import com.firmys.gameservices.service.GameService;
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

  private final Class<Attribute> entityType = Attribute.class;
}
