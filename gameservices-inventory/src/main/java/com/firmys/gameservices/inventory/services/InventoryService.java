package com.firmys.gameservices.inventory.services;

import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.inventory.models.Inventory;
import com.firmys.gameservices.inventory.repositories.InventoryRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class InventoryService extends CommonService<Inventory> {
  private final InventoryRepository repository;
}
