package com.firmys.gameservices.service.error;

import com.firmys.gameservices.generated.models.Error;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class WebException extends ServiceException {

  private final Error serviceError;

  public WebException(Error serviceError) {
    super(serviceError);
    this.serviceError = serviceError;
  }

  public static WebException create(Error serviceError) {
    return new WebException(serviceError);
  }

  @Override
  public String getMessage() {
    return serviceError.toJson();
  }
}
