package com.firmys.gameservice.inventory.service.item;

import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServiceConstants.ITEM + ServiceConstants.CACHE_MANAGER_SUFFIX)
public class ItemCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceConstants.ITEM;

    public ItemCacheManager() {
        super(cacheName);
    }
}
