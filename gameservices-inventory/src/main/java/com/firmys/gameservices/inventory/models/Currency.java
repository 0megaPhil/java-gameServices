package com.firmys.gameservices.inventory.models;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.data.Attribute;
import java.util.Set;
import java.util.UUID;

public record Currency(UUID uuid, String name, String description, Set<Attribute> attributes)
    implements CommonEntity {}
