package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.CommonObject;
import lombok.Builder;

@Builder(toBuilder = true)
public record Attribute(AttributesType type, Long value) implements CommonObject {}
