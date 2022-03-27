package com.firmys.gameservice.inventory.service.inventory;

import com.firmys.gameservice.common.GameDataLookup;
import com.firmys.gameservice.inventory.service.data.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryDataLookup extends GameDataLookup<Inventory> {

    public InventoryDataLookup(InventoryService service) {
        super(service);
    }

}
