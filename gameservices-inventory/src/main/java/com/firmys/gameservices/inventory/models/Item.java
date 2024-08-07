package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.Fungible;
import com.firmys.gameservices.common.data.Attribute;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder(toBuilder = true)
public record Item(@With @Id UUID uuid, String name, String description, Set<Attribute> attributes)
    implements CommonEntity, Fungible {}
