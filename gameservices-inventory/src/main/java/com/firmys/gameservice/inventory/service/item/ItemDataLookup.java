package com.firmys.gameservice.inventory.service.item;

import com.firmys.gameservice.common.GameDataLookup;
import com.firmys.gameservice.inventory.service.data.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDataLookup extends GameDataLookup<Item> {

    public ItemDataLookup(ItemService service) {
        super(service);
    }

}
