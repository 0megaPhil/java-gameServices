package com.firmys.gameservices.transactions;

import com.firmys.gameservices.common.ServiceConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(
    info =
        @Info(
            title = ServiceConstants.TRANSACTIONS_SERVICES,
            version = ServiceConstants.VERSION,
            description = "Game Services API"))
public class TransactionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(TransactionsApplication.class, args);
  }
}
