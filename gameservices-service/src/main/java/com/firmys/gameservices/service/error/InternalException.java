package com.firmys.gameservices.service.error;

import com.firmys.gameservices.generated.models.Error;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InternalException extends ServiceException {

  private final Error serviceError;

  public InternalException(Error serviceError) {
    super(serviceError);
    this.serviceError = serviceError;
  }

  public static InternalException create(Error serviceError) {
    return new InternalException(serviceError);
  }

  @Override
  public String getMessage() {
    return serviceError.toJson();
  }
}
