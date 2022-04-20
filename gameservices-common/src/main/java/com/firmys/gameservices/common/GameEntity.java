package com.firmys.gameservices.common;

import java.util.UUID;

public interface GameEntity extends GameData {
    UUID getUuid();
    int getId();
}
