package com.firmys.gameservices.service.error;

import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.common.error.CommonException;
import com.firmys.gameservices.generated.models.Error;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
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

  public ServiceException(Error serviceError, Exception exception) {
    super(serviceError.toJson(), exception);
    this.serviceError = serviceError;
  }

  public ServiceException(Throwable exception) {
    super(exception);
    this.serviceError = generateError(exception).withErrorCode(500);
  }

  public ServiceException(Throwable exception, int errorCode) {
    super(exception);
    this.serviceError = generateError(exception).withErrorCode(errorCode);
  }

  @Override
  public String getMessage() {
    if (serviceError == null) {
      return generateError(this).toJson();
    }
    return serviceError.toJson();
  }

  private Error generateError(Throwable throwable) {
    String failOverMsg = "null pointer exception, no GameServiceError populated";
    Throwable orFailover = Optional.ofNullable(throwable).orElse(new RuntimeException(failOverMsg));
    return Error.builder()
        .title(orFailover.getClass().getTypeName())
        .data(
            JsonUtils.JSON.toJson(
                Arrays.stream(orFailover.getStackTrace())
                    .filter(st -> st.getClassName().contains(".firmys"))
                    .limit(10)
                    .collect(Collectors.toSet())))
        .message(orFailover.getMessage())
        .build();
  }
}
