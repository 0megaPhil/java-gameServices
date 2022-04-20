package com.firmys.gameservices.inventory.service.item;

import com.firmys.gameservices.common.GameDataLookup;
import com.firmys.gameservices.inventory.service.data.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDataLookup extends GameDataLookup<Item> {

    public ItemDataLookup(ItemService service) {
        super(service);
    }

}
