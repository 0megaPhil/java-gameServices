package com.firmys.gameservices.common;

import com.firmys.gameservices.generated.models.Flavor;
import java.util.UUID;

public interface CommonEntity extends CommonObject {

  UUID uuid();

  CommonEntity withUuid(UUID uuid);

  CommonEntity withFlavor(Flavor flavor);
}
