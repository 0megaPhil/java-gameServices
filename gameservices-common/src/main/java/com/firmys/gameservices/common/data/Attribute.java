package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.CommonObject;

public record Attribute(AttributesType type, Long value) implements CommonObject {}
