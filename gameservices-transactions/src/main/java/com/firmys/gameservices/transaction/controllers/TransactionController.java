package com.firmys.gameservices.transaction.controllers;

import static com.firmys.gameservices.common.CommonConstants.*;

import com.firmys.gameservices.generated.models.Transaction;
import com.firmys.gameservices.service.ServiceController;
import com.firmys.gameservices.transaction.services.TransactionService;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@Getter
@RestController
@Builder(toBuilder = true)
@RequestMapping(TRANSACTION_PATH)
@Accessors(chain = true, fluent = true)
public class TransactionController extends ServiceController<Transaction> {

  private final TransactionService service;
  private final Class<Transaction> entityClass = Transaction.class;
}
