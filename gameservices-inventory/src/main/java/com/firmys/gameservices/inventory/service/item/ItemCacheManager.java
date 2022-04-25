package com.firmys.gameservices.inventory.service.item;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServiceStrings.ITEM + ServiceStrings.CACHE_MANAGER_SUFFIX)
public class ItemCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceStrings.ITEM;

    public ItemCacheManager() {
        super(cacheName);
    }
}
