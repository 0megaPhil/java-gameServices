package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceException;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FunctionUtils {

  public static <T> T orVoid(Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      log.debug(
          "Exception caught {}: {}, " + "returning null intentionally",
          e.getClass().getSimpleName(),
          e.getMessage());
      return null;
    }
  }

  public static <T> T orThrow(Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      throw new GameServiceException(e);
    }
  }
}
