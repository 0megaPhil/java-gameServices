package com.firmys.gameservices.characters.models;

import com.firmys.gameservices.common.CommonEntity;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Attribute(@With @Id UUID uuid, String name, String description)
    implements CommonEntity {}
