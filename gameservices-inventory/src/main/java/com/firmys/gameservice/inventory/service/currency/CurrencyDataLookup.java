package com.firmys.gameservice.inventory.service.currency;

import com.firmys.gameservice.common.GameDataLookup;
import com.firmys.gameservice.inventory.service.data.Currency;
import org.springframework.stereotype.Component;

@Component
public class CurrencyDataLookup extends GameDataLookup<Currency> {

    public CurrencyDataLookup(CurrencyService service) {
        super(service);
    }

}
