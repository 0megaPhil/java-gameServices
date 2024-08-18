package com.firmys.gameservices.service.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import graphql.GraphQLError;
import graphql.GraphqlErrorException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GraphQLExceptionController {

  @ResponseStatus(BAD_REQUEST)
  @GraphQlExceptionHandler(ServiceException.class)
  public Mono<GraphQLError> serviceException(ServiceException serviceException) {
    return Mono.just(
        graphql.GraphQLError.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(serviceException.getServiceError().toJson())
            .build());
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @GraphQlExceptionHandler(GraphqlErrorException.class)
  public Mono<GraphQLError> serviceException(GraphqlErrorException graphQLException) {
    return Mono.just(graphQLException);
  }

  private ErrorType resolve(int errorCode) {
    return switch (errorCode) {
      case 401 -> ErrorType.UNAUTHORIZED;
      case 403 -> ErrorType.FORBIDDEN;
      case 404 -> ErrorType.NOT_FOUND;
      default -> ErrorType.INTERNAL_ERROR;
    };
  }

  /**
   * Fail-over exception handler for services
   *
   * @param exception general exception
   * @return response which includes GameServiceException
   */
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @GraphQlExceptionHandler(Exception.class)
  public Mono<GraphQLError> generalException(Exception exception) {
    return Mono.just(
        graphql.GraphQLError.newError()
            .errorType(resolve(new ServiceException(exception).getServiceError().errorCode()))
            .errorType(ErrorType.INTERNAL_ERROR)
            .message(new ServiceException(exception).getServiceError().toJson())
            .build());
  }
}
