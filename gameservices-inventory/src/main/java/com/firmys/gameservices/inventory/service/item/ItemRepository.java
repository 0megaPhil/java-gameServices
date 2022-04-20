package com.firmys.gameservices.inventory.service.item;

import com.firmys.gameservices.inventory.service.data.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
}
