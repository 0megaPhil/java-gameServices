package com.firmys.gameservices.service.error;

import static com.firmys.gameservices.service.error.ErrorUtils.webError;

import com.firmys.gameservices.generated.models.ErrorResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ServiceExceptionController {

  @ExceptionHandler(ServiceException.class)
  public Mono<ErrorResponse> serviceException(ServiceException serviceException) {
    return Mono.just(ErrorResponse.builder().error(serviceException.getServiceError()).build());
  }

  /**
   * Fail-over exception handler for services
   *
   * @param exception general exception
   * @return response which includes GameServiceException
   */
  @ExceptionHandler(Exception.class)
  public Mono<ErrorResponse> generalException(Exception exception, ServerHttpRequest request) {
    return Mono.just(ErrorResponse.builder().error(webError().apply(exception, request)).build());
  }
}
