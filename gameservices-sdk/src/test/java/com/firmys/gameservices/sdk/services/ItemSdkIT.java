package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.config.SdkConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = {SdkConfig.class})
public class ItemSdkIT {

    @Autowired
    ItemSdk sdk;

    @Test
    public void create() {
        Item generatedItem = EntityGenerators.generateItem();
        Mono<Item> createdItem = sdk.createItem(generatedItem);
        AtomicReference<UUID> itemUuid = new AtomicReference<>();
        createdItem.map(m -> {
                    itemUuid.set(m.getUuid());
                    System.out.println("GENERATED: " + generatedItem);
                    System.out.println("CREATED: " + createdItem);
                    Assertions.assertThat(generatedItem.getName()).isEqualTo(m.getName());
                    Assertions.assertThat(generatedItem.getBaseValue()).isEqualTo(m.getBaseValue());
                    Assertions.assertThat(generatedItem.getDescription()).isEqualTo(m.getDescription());
                    Assertions.assertThat(generatedItem.getHeight()).isEqualTo(m.getHeight());
                    Assertions.assertThat(generatedItem.getLength()).isEqualTo(m.getLength());
                    Assertions.assertThat(generatedItem.getWidth()).isEqualTo(m.getWidth());
                    Assertions.assertThat(generatedItem.getWeight()).isEqualTo(m.getWeight());
                    Assertions.assertThat(generatedItem.getRequirements()).isEqualTo(m.getRequirements());
                    return m;
                }).subscribeOn(Schedulers.parallel())
                .then()
                .block();

        sdk.deleteItem(itemUuid.get())
                .then()
                .block();
    }

}
