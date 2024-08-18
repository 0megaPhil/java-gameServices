package com.firmys.gameservices.service.error;

import com.firmys.gameservices.common.error.CommonException;
import com.firmys.gameservices.generated.models.Error;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ServiceException extends CommonException {

  private final Error serviceError;

  public ServiceException(Error serviceError) {
    super(serviceError.toJson());
    this.serviceError = serviceError;
  }

  public static ServiceException create(Error serviceError) {
    return new ServiceException(serviceError);
  }

  @Override
  public String getMessage() {
    return serviceError.toJson();
  }
}
