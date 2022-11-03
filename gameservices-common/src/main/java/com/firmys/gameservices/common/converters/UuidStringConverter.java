package com.firmys.gameservices.common.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UuidStringConverter implements Converter<String, UUID> {
    @Override
    public UUID convert(@Nullable String source) {
        return UUID.fromString(Optional.ofNullable(source).orElseThrow());
    }
}
