package com.firmys.gameservices.inventory;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.config.GameServiceCommonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Import(GameServiceCommonConfig.class)
@OpenAPIDefinition(info = @Info(title = ServiceConstants.INVENTORY_SERVICE,
        version = ServiceConstants.VERSION, description = "Game Services API"))
public class InventoryServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApp.class, args);
    }

}
