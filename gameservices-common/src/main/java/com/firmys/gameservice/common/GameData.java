package com.firmys.gameservice.common;

import java.util.UUID;

public interface GameData {
    UUID getUuid();
    int getId();
    void update(GameData gameData);
}
