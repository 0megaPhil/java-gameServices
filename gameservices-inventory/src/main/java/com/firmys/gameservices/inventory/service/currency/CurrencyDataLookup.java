package com.firmys.gameservices.inventory.service.currency;

import com.firmys.gameservices.common.GameDataLookup;
import com.firmys.gameservices.inventory.service.data.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDataLookup extends GameDataLookup<Currency> {

    public CurrencyDataLookup(CurrencyService service) {
        super(service);
    }

}
