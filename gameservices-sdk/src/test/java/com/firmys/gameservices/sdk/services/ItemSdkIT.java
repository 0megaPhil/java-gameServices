package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.config.SdkConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SdkConfig.class, ItemSdk.class})
public class ItemSdkIT {

    @Autowired
    ItemSdk sdk;

    @Test
    public void create() {
        Item generatedItem = InventoryTestUtilities.generateItem();
        Item createdItem = sdk.addItem(generatedItem).block();
        Assertions.assertThat(generatedItem.getName()).isEqualTo(createdItem.getName());
        Assertions.assertThat(generatedItem.getBaseValue()).isEqualTo(createdItem.getBaseValue());
        Assertions.assertThat(generatedItem.getDescription()).isEqualTo(createdItem.getDescription());
        Assertions.assertThat(generatedItem.getHeight()).isEqualTo(createdItem.getHeight());
        Assertions.assertThat(generatedItem.getLength()).isEqualTo(createdItem.getLength());
        Assertions.assertThat(generatedItem.getWidth()).isEqualTo(createdItem.getWidth());
        Assertions.assertThat(generatedItem.getWeight()).isEqualTo(createdItem.getWeight());
        Assertions.assertThat(generatedItem.getRequirements()).isEqualTo(createdItem.getRequirements());

        System.out.println("GENERATED: " + generatedItem);
        System.out.println("CREATED: " + createdItem);

        sdk.deleteItem(null, createdItem).block();
        Item find = sdk.findByUuidParamItem(createdItem.getUuid().toString()).block();
        Assertions.assertThat(find.getUuid()).isNull();
    }

}
