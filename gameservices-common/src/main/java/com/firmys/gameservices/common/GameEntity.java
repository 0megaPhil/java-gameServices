package com.firmys.gameservices.common;

import java.util.UUID;

/**
 * Distinguish between {@link GameData} and {@link GameEntity} as GameEntity objects are used to generate
 * tables via hibernate
 */
public interface GameEntity extends GameData {
    UUID getUuid();
    int getId();
}
