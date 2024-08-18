package com.firmys.gameservices.service.error;

import com.firmys.gameservices.generated.models.Error;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class MongoDBException extends ServiceException {

  private final Error serviceError;

  public MongoDBException(Error serviceError) {
    super(serviceError);
    this.serviceError = serviceError;
  }

  public static MongoDBException create(Error serviceError) {
    return new MongoDBException(serviceError);
  }

  @Override
  public String getMessage() {
    return serviceError.toJson();
  }
}
