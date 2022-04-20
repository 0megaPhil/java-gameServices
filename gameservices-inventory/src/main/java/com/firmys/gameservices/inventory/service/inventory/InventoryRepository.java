package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.inventory.service.data.Inventory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Integer> {
}
