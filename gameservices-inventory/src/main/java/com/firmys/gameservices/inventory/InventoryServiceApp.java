package com.firmys.gameservices.inventory;

import com.firmys.gameservices.common.config.GameServiceCommonConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GameServiceCommonConfig.class)
@OpenAPIDefinition(info = @Info(title = "InventoryService", version = "0.1.0", description = "Documentation APIs v0.1.0"))
public class InventoryServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApp.class, args);
    }

}
