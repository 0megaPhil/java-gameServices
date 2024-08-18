package com.firmys.gameservices.inventory.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.generated.models.Inventory;
import com.firmys.gameservices.inventory.services.InventoryService;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(INVENTORY_PATH)
@Accessors(chain = true, fluent = true)
public class InventoryController extends ServiceController<Inventory> {

  private final InventoryService service;
  private final Class<Inventory> entityClass = Inventory.class;
}
