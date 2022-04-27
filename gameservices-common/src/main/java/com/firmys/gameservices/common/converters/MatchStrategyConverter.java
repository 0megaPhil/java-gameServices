package com.firmys.gameservices.common.converters;

import com.firmys.gameservices.common.MatchStrategy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MatchStrategyConverter implements Converter<String, MatchStrategy> {
    @Override
    public MatchStrategy convert(@Nullable String source) {
        return MatchStrategy.valueOf(Optional.ofNullable(source).orElse(MatchStrategy.ALL.getStrategyString()));
    }
}
