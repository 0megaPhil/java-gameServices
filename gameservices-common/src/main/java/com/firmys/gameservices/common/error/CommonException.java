package com.firmys.gameservices.common.error;

import java.io.Serializable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class CommonException extends RuntimeException implements Serializable {

  public CommonException(String message) {
    super(message);
  }

  public CommonException(Throwable throwable) {
    super(throwable);
  }

  public CommonException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
