package com.firmys.gameservices.common.error;

import com.firmys.gameservices.generated.models.Error;
import java.io.Serializable;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ServiceException extends RuntimeException implements Serializable {

  private Error serviceError;

  public ServiceException(Error serviceError) {
    super(serviceError.toJson());
    this.serviceError = serviceError;
    setStackTrace(Thread.currentThread().getStackTrace());
  }

  public ServiceException(Error serviceError, Exception exception) {
    super(serviceError.toJson(), exception);
    this.serviceError = serviceError;
  }

  public ServiceException(Throwable exception) {
    super(exception);
    this.serviceError =
        Error.builder()
            .name(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .errorCode(500)
            .build();
  }

  public String getMessage() {
    if (serviceError == null) {
      String failOverMsg = "null pointer exception, no GameServiceError populated";
      RuntimeException failoverCause = new RuntimeException(failOverMsg);
      serviceError =
          Error.builder()
              .name(
                  Optional.ofNullable(super.getCause())
                      .orElse(failoverCause)
                      .getClass()
                      .getSimpleName())
              .message(Optional.ofNullable(super.getMessage()).orElse(failOverMsg))
              .build();
    }
    return serviceError.message();
  }

  public synchronized Throwable getCause() {
    return super.getCause();
  }
}
