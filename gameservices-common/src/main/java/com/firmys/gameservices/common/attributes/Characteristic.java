package com.firmys.gameservices.common.attributes;

import com.firmys.gameservices.common.data.Attribute;
import lombok.Builder;
import lombok.With;

@With
@Builder(toBuilder = true)
public record Characteristic(String name, Long value, String description)
    implements Attribute<String, Long> {}
