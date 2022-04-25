package com.firmys.gameservices.inventory.service.currency;

import com.firmys.gameservices.common.GameDataCacheManager;
import com.firmys.gameservices.common.ServiceStrings;
import org.springframework.stereotype.Component;

@Component(ServiceStrings.CURRENCY + ServiceStrings.CACHE_MANAGER_SUFFIX)
public class CurrencyCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceStrings.CURRENCY;

    public CurrencyCacheManager() {
        super(cacheName);
    }
}
