package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.config.SdkConfig;
import com.firmys.gameservices.sdk.services.utilities.EntityGenerators;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(classes = {SdkConfig.class})
public class ItemsSdkIT extends SdkBase {

    @Autowired
    ItemsSdk sdk;

    @Autowired
    ItemSdk item;

    @Test
    public void create() {
        Set<Item> generatedItems = IntStream.range(0, new Random().nextInt(1, 9))
                .mapToObj(i -> EntityGenerators.generateItem()).collect(Collectors.toSet());
        Mono<Set<Item>> createdItems = sdk.createSetItem(generatedItems);

        createdItems.map(set -> set.stream()
                        .peek(m -> System.out.println("CREATED: " + m))
                        .collect(Collectors.toSet())).subscribeOn(Schedulers.parallel())
                .then().block();
    }

    @Test
    public void queryOne() {
        Item generated = EntityGenerators.generateItem();
        Mono<Item> createdItem = item.createItem(generated);
        Item handled = handleMono(createdItem);
        Set<String> fieldSet = Arrays.stream(Item.class.getDeclaredFields()).map(Field::getName)
                .filter(f -> !f.contains("UID")).collect(Collectors.toSet());
        Map<String, Object> queryFields = fieldSet.stream().map(s -> {
            try {
                Field field = Item.class.getDeclaredField(s);
                field.setAccessible(true);
                return Map.entry(s, field.get(generated));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                return Map.entry("private", "field");
            }
        }).filter(e -> !e.getKey().contains("private")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Set<Mono<Set<Item>>> monoSet = queryFields.entrySet().stream()
                .map(e -> sdk.findAllItem(Map.of(e.getKey(),
                        e.getValue().toString()))).collect(Collectors.toSet());

        monoSet.forEach(ms -> {
            ReactiveAssertion<Set<Item>> assertion = new ReactiveAssertion<>();
            assertion.withAssertFunction(set -> {
                    set.forEach(item -> {
                        System.out.println("NAME: " + generated.getName() + " - Found: " + item.getName());
                        System.out.println("VALUE: " + generated.getBaseValue() + " - Found: " + item.getBaseValue());
                        System.out.println("DESC: " + generated.getDescription() + " - Found: " + item.getDescription());
                        System.out.println("HEIGHT: " + generated.getHeight() + " - Found: " + item.getHeight());
                        System.out.println("LENGTH: " + generated.getLength() + " - Found: " + item.getLength());
                        System.out.println("WIDTH: " + generated.getWidth() + " - Found: " + item.getWidth());
                        System.out.println("WEIGHT: " + generated.getWeight() + " - Found: " + item.getWeight());
                        System.out.println("REQS: " + generated.getItemRequirements() + " - Found: " + item.getItemRequirements());
                        Assertions.assertThat(item.getName()).isEqualTo(generated.getName());
                        Assertions.assertThat(item.getBaseValue()).isEqualTo(generated.getBaseValue());
                        Assertions.assertThat(item.getDescription()).isEqualTo(generated.getDescription());
                        Assertions.assertThat(item.getHeight()).isEqualTo(generated.getHeight());
                        Assertions.assertThat(item.getLength()).isEqualTo(generated.getLength());
                        Assertions.assertThat(item.getWidth()).isEqualTo(generated.getWidth());
                        Assertions.assertThat(item.getWeight()).isEqualTo(generated.getWeight());
                        Assertions.assertThat(item.getItemRequirements()).isEqualTo(generated.getItemRequirements());
                    });
                    return set;
            }).handle().accept(ms);
        });
//        item.deleteItem(handled.getUuid()).then().block();
    }

    @Test
    void queryAll() {
        Item generated = EntityGenerators.generateItem();
        Mono<Item> createdItem = item.createItem(generated);
        Item handled = handleMono(createdItem);
        Set<String> fieldSet = Arrays.stream(Item.class.getDeclaredFields()).map(Field::getName)
                .filter(f -> !f.contains("UID")).collect(Collectors.toSet());
        Map<String, String> queryFields = fieldSet.stream().map(s -> {
            try {
                Field field = Item.class.getDeclaredField(s);
                field.setAccessible(true);
                return Map.entry(s, field.get(generated).toString());
            } catch (IllegalAccessException | NoSuchFieldException e) {
                return Map.entry("private", "field");
            }
        }).filter(e -> !e.getKey().contains("private")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Mono<Set<Item>> found = sdk.findAllItem(queryFields);

        ReactiveAssertion<Set<Item>> assertion = new ReactiveAssertion<>();
        assertion.withAssertFunction(set -> {
            Assertions.assertThat(set.size()).isEqualTo(1);
            set.forEach(item -> {
                System.out.println("NAME: " + generated.getName() + " - Found: " + item.getName());
                System.out.println("VALUE: " + generated.getBaseValue() + " - Found: " + item.getBaseValue());
                System.out.println("DESC: " + generated.getDescription() + " - Found: " + item.getDescription());
                System.out.println("HEIGHT: " + generated.getHeight() + " - Found: " + item.getHeight());
                System.out.println("LENGTH: " + generated.getLength() + " - Found: " + item.getLength());
                System.out.println("WIDTH: " + generated.getWidth() + " - Found: " + item.getWidth());
                System.out.println("WEIGHT: " + generated.getWeight() + " - Found: " + item.getWeight());
                System.out.println("REQS: " + generated.getItemRequirements() + " - Found: " + item.getItemRequirements());
                Assertions.assertThat(item.getName()).isEqualTo(generated.getName());
                Assertions.assertThat(item.getBaseValue()).isEqualTo(generated.getBaseValue());
                Assertions.assertThat(item.getDescription()).isEqualTo(generated.getDescription());
                Assertions.assertThat(item.getHeight()).isEqualTo(generated.getHeight());
                Assertions.assertThat(item.getLength()).isEqualTo(generated.getLength());
                Assertions.assertThat(item.getWidth()).isEqualTo(generated.getWidth());
                Assertions.assertThat(item.getWeight()).isEqualTo(generated.getWeight());
                Assertions.assertThat(item.getItemRequirements()).isEqualTo(generated.getItemRequirements());
            });
            return set;
        }).handle().accept(found);
        item.deleteItem(handled.getUuid()).then().block();
    }

}
