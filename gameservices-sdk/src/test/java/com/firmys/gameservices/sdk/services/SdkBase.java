package com.firmys.gameservices.sdk.services;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class SdkBase {

    public <T> T handleMono(Mono<T> mono) {
        AtomicReference<T> object = new AtomicReference<>();
        mono.map(m -> {

                    return m;
                }).subscribeOn(Schedulers.parallel())
                .map(m -> {
                    object.set(m);
                    return m;
                })
                .then()
                .block();
        return object.get();
    }

}
