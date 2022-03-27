package com.firmys.gameservice.inventory.impl;

import com.firmys.gameservice.inventory.service.data.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepositoryOld extends CrudRepository<Item, Integer> {
}
