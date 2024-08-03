package com.firmys.gameservices.common.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.firmys.gameservices.common.CommonObject;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Flavor(String targetId, String text, String objectType) implements CommonObject {}
