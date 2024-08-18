package com.firmys.gameservices.data;

import com.firmys.gameservices.generated.models.CommonEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommonRepository<T extends CommonEntity>
    extends ReactiveMongoRepository<T, ObjectId> {}
