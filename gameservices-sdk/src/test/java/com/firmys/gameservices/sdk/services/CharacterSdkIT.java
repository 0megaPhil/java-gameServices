package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.models.Inventory;
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
public class CharacterSdkIT extends SdkBase {

    @Autowired
    CharacterSdk sdk;

    @Autowired
    InventorySdk inventory;

    @Autowired
    ItemSdk item;

    @Autowired
    CurrencySdk currency;

    @Test
    void addCharacter() {

        Character generated = EntityGenerators.generateCharacter();
        Mono<Character> characterMono = sdk.createCharacter(generated);
        AtomicReference<UUID> uuid = new AtomicReference<>();
//        Character created = handleMono(characterMono);

        characterMono.map(m -> {
                    uuid.set(m.getUuid());
                    System.out.println("GENERATED: " + generated);
                    System.out.println("CREATED: " + m);
                    Assertions.assertThat(generated.getName()).isEqualTo(m.getName());
                    Assertions.assertThat(generated.getDescription()).isEqualTo(m.getDescription());
                    Assertions.assertThat(generated.getHeight()).isEqualTo(m.getHeight());
                    Assertions.assertThat(generated.getWeight()).isEqualTo(m.getWeight());
                    Assertions.assertThat(generated.getAge()).isEqualTo(m.getAge());
                    Assertions.assertThat(generated.getGender()).isEqualTo(m.getGender());
//                    Assertions.assertThat(generated.getInventoryId()).isEqualTo(m.getInventoryId());
                    return m;
                }).subscribeOn(Schedulers.parallel())
                .then()
                .block();
    }


}
