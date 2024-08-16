package com.firmys.gameservices.transaction.controllers;

import static com.firmys.gameservices.common.CommonConstants.CURRENCY_PATH;

import com.firmys.gameservices.common.CommonController;
import com.firmys.gameservices.generated.models.Currency;
import com.firmys.gameservices.transaction.services.CurrencyService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(CURRENCY_PATH)
@Accessors(chain = true, fluent = true)
public class CurrencyController extends CommonController<Currency> {

  private final CurrencyService service;
  private final Class<Currency> entityClass = Currency.class;
}
