package com.firmys.gameservices.service.error;

import com.firmys.gameservices.generated.models.Error;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class FlavorException extends ServiceException {

  private final Error serviceError;

  public FlavorException(Error serviceError) {
    super(serviceError);
    this.serviceError = serviceError;
  }

  public static FlavorException create(Error serviceError) {
    return new FlavorException(serviceError);
  }

  @Override
  public String getMessage() {
    return serviceError.toJson();
  }
}
