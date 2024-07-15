package com.firmys.gameservices.inventory.services;

import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.inventory.models.Transaction;
import com.firmys.gameservices.inventory.repositories.TransactionRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class TransactionService extends CommonService<Transaction> {
  private final TransactionRepository repository;
}
