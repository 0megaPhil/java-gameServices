package com.firmys.gameservices.common;

import java.util.UUID;

public interface CommonEntity extends CommonObject {

  UUID uuid();

  CommonEntity withUuid(UUID uuid);
}
