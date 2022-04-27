package com.firmys.gameservices.inventory.service.item;

import com.firmys.gameservices.common.GameService;
import com.firmys.gameservices.inventory.service.data.Item;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends
        PagingAndSortingRepository<Item, Integer>, QuerydslPredicateExecutor<Item> {
}
