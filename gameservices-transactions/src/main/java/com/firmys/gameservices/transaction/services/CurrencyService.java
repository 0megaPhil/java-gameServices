package com.firmys.gameservices.transaction.services;

import com.firmys.gameservices.generated.models.Currency;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.transaction.repositories.CurrencyRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CurrencyService extends GameService<Currency> {
  private final CurrencyRepository repository;
  private final Class<Currency> entityType = Currency.class;
}
