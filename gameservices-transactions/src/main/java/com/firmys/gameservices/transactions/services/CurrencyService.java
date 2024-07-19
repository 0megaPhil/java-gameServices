package com.firmys.gameservices.transactions.services;

import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.transactions.models.Currency;
import com.firmys.gameservices.transactions.repositories.CurrencyRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CurrencyService extends CommonService<Currency> {
  private final CurrencyRepository repository;
}
