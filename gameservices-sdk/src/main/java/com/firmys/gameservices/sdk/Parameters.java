package com.firmys.gameservices.sdk;

import org.springframework.util.LinkedMultiValueMap;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Parameters {
    private final LinkedMultiValueMap<String, String> parameterMap;

    public Parameters(Builder builder) {
        this.parameterMap = builder.parameterMap;
    }

    public static Parameters.Builder builder() {
        return new Parameters.Builder();
    }

    public static class Builder {
        private final LinkedMultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<>();

        public Builder() {
        }

        public Builder withParam(String paramKey, String... paramValues) {
            applyParameter(paramKey).accept(paramValues);
            return this;
        }

        public Builder withParam(String paramKey, Set<String> paramValues) {
            applyParameter(paramKey).accept(paramValues.toArray(new String[]{}));
            return this;
        }

        public Builder withParams(Map<String, String[]> paramMap) {
            paramMap.forEach((key, value) -> applyParameter(key).accept(value));
            return this;
        }

        private Consumer<String[]> applyParameter(String paramName) {
            return params -> {
                Arrays.stream(params).filter(Objects::nonNull)
                        .forEach(p -> parameterMap.computeIfAbsent(paramName, v -> new ArrayList<>()).add(p));
            };
        }

        public Parameters build() {
            return new Parameters(this);
        }
    }

    public LinkedMultiValueMap<String, String> get() {
        return parameterMap;
    }
}
