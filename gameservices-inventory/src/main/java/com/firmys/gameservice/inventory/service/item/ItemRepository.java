package com.firmys.gameservice.inventory.service.item;

import com.firmys.gameservice.inventory.service.data.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
}
