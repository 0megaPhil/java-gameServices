package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.sdk.config.SdkConfig;
import com.firmys.gameservices.sdk.services.utilities.EntityGenerators;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@SpringBootTest(classes = {SdkConfig.class})
public class CurrencySdkIT extends SdkBase {

    @Autowired
    CurrencySdk sdk;

    @Autowired
    InventorySdk inventory;

    @Autowired
    ItemSdk item;

    @Autowired
    CharacterSdk currency;

    @Test
    void addCharacter() {

        Currency generated = EntityGenerators.generateCurrency();
        Mono<Currency> characterMono = sdk.createCurrency(generated);
        characterMono.map(m -> {
                    System.out.println("GENERATED: " + generated);
                    System.out.println("CREATED: " + m);
                    Assertions.assertThat(generated.getName()).isEqualTo(m.getName());
                    Assertions.assertThat(generated.getDescription()).isEqualTo(m.getDescription());
                    Assertions.assertThat(generated.getBaseValue()).isEqualTo(m.getBaseValue());
                    return m;
                }).subscribeOn(Schedulers.parallel())
                .then()
                .block();
    }


}
