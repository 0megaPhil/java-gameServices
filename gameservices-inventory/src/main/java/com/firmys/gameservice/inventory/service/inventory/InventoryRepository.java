package com.firmys.gameservice.inventory.service.inventory;

import com.firmys.gameservice.inventory.service.data.Inventory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Integer> {
}
