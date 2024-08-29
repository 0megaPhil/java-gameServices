package com.firmys.gameservices.inventory.services;

import com.firmys.gameservices.generated.models.Item;
import com.firmys.gameservices.inventory.repositories.ItemRepository;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class ItemService extends GameService<Item> {
  private final ItemRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Item> entityType = Item.class;
}
