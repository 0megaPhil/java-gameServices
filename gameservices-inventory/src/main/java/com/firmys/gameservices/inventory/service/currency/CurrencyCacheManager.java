package com.firmys.gameservices.inventory.service.currency;

import com.firmys.gameservices.common.GameDataCacheManager;
import com.firmys.gameservices.common.ServiceConstants;
import org.springframework.stereotype.Component;

@Component(ServiceConstants.CURRENCY + ServiceConstants.CACHE_MANAGER_SUFFIX)
public class CurrencyCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceConstants.CURRENCY;

    public CurrencyCacheManager() {
        super(cacheName);
    }
}
