package com.firmys.gameservices.world.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record World(@With @Id UUID uuid) implements CommonEntity {}
