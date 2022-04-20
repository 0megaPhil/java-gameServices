package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.GameDataLookup;
import com.firmys.gameservices.inventory.service.data.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryDataLookup extends GameDataLookup<Inventory> {

    public InventoryDataLookup(InventoryService service) {
        super(service);
    }

}
