package com.firmys.gameservice.common;

import org.springframework.lang.Nullable;

import java.util.UUID;

public class AbstractController {

    public UUID getUuidFromBodyOrParam(GameData gameData, String uuidParam) {
        if(gameData != null) {
            return gameData.getUuid();
        } else if(uuidParam != null) {
            return UUID.fromString(uuidParam);
        } else {
            throw new RuntimeException("No uuid found in body or parameters");
        }
    }
}
