package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServiceStrings.INVENTORY + ServiceStrings.CACHE_MANAGER_SUFFIX)
public class InventoryCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceStrings.INVENTORY;

    public InventoryCacheManager() {
        super(cacheName);
    }
}
