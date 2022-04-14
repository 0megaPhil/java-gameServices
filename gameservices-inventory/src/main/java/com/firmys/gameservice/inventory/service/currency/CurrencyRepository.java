package com.firmys.gameservice.inventory.service.currency;

import com.firmys.gameservice.inventory.service.data.Currency;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CurrencyRepository extends PagingAndSortingRepository<Currency, Integer> {
}
