package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceException;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FunctionUtils {

  /**
   * Executes the given callable and returns its result. If an exception is thrown during the
   * execution, the exception message is logged as debug and null is returned.
   *
   * @param callable the callable to execute
   * @param <T> the type of the result
   * @return the result of the callable, or null if an exception is thrown
   */
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
