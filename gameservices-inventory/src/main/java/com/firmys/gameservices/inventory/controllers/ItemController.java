package com.firmys.gameservices.inventory.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.generated.models.Item;
import com.firmys.gameservices.inventory.services.ItemService;
import com.firmys.gameservices.service.ServiceController;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(ITEM_PATH)
@Accessors(chain = true, fluent = true)
public class ItemController extends ServiceController<Item> {

  private final ItemService service;
  private final Class<Item> entityClass = Item.class;
}
