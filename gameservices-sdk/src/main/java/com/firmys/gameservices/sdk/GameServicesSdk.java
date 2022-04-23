package com.firmys.gameservices.sdk;

import com.firmys.gameservices.sdk.services.InventoriesSdk;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
public class GameServicesSdk {
    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication();
        context = app.run(GameServicesSdk.class, args);
        SpringApplication.run(GameServicesSdk.class, args);
    }

    public InventoriesSdk inventories() {
        if(context == null) {
            main(new String[]{});
        }
        return context.getBean(InventoriesSdk.class);
    }
}
