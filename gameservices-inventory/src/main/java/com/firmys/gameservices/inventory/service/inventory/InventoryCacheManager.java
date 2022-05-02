package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServiceConstants.INVENTORY + ServiceConstants.CACHE_MANAGER_SUFFIX)
public class InventoryCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceConstants.INVENTORY;

    public InventoryCacheManager() {
        super(cacheName);
    }
}
