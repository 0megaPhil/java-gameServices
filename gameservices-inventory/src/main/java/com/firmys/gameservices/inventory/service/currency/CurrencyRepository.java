package com.firmys.gameservices.inventory.service.currency;

import com.firmys.gameservices.inventory.service.data.Currency;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CurrencyRepository extends
        PagingAndSortingRepository<Currency, Integer>, QuerydslPredicateExecutor<Currency> {
}
