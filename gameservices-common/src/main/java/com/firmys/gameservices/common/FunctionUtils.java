package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.ServiceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

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

  public static <T> List<T> safeList(Collection<T> collection) {
    try {
      return Optional.ofNullable(collection).map(List::copyOf).orElseGet(ArrayList::new);
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  public static <T> Set<T> safeSet(Collection<T> collection) {
    try {
      return Optional.ofNullable(collection)
          .map(set -> set.stream().filter(Objects::nonNull).collect(Collectors.toSet()))
          .orElseGet(HashSet::new);
    } catch (Exception e) {
      return new HashSet<>();
    }
  }

  public static <T> Flux<T> safeFlux(Flux<T> flux) {
    return Optional.ofNullable(flux).orElseGet(Flux::empty);
  }

  public static <T> T orThrow(Callable<T> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      log.error("{}", e.getClass().getSimpleName(), e);
      throw new ServiceException(e);
    }
  }
}
