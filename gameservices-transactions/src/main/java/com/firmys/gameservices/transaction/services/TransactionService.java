package com.firmys.gameservices.transaction.services;

import com.firmys.gameservices.generated.models.Transaction;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import com.firmys.gameservices.transaction.repositories.TransactionRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class TransactionService extends GameService<Transaction> {
  private final TransactionRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Transaction> entityType = Transaction.class;
}
