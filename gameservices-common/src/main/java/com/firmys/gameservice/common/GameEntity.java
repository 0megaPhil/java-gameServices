package com.firmys.gameservice.common;

import java.util.UUID;

public interface GameEntity extends GameData {
    UUID getUuid();
    int getId();
}
