package com.firmys.gameservices.transaction;

import com.firmys.gameservices.common.CommonConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = CommonConstants.TRANSACTION_SERVICES,
            version = CommonConstants.VERSION,
            description = "Game Services API"))
public class TransactionsApplication {

  public static void main(String[] args) {
    SpringApplication.run(TransactionsApplication.class, args);
  }
}
