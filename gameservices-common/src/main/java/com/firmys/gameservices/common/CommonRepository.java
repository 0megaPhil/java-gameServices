package com.firmys.gameservices.common;

import com.firmys.gameservices.generated.models.CommonEntity;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CommonRepository<T extends CommonEntity> extends R2dbcRepository<T, UUID> {}
