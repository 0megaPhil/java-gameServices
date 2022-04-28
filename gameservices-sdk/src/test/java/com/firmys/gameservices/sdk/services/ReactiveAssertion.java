package com.firmys.gameservices.sdk.services;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public class ReactiveAssertion<T> {
    private final Set<Function<T, T>> functions = ConcurrentHashMap.newKeySet();

    public ReactiveAssertion() {}

    public ReactiveAssertion<T> withAssertFunction(Function<T, T> assertionFunction) {
        functions.add(assertionFunction);
        return this;
    }

    public Consumer<Mono<T>> handle() {
        return mono -> {
            AtomicReference<Mono<T>> monoRef = new AtomicReference<>();
            monoRef.set(mono);
            monoRef.get().map(m -> {
                        functions.forEach(f -> f.apply(m));
                        return m;
                    }).subscribeOn(Schedulers.parallel())
                    .then()
                    .onErrorResume(Mono::error)
                    .block();
        };
    }

}
