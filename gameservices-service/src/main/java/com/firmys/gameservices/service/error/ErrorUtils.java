package com.firmys.gameservices.service.error;

import static com.firmys.gameservices.common.FunctionUtils.orVoid;

import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.CommonObject;
import com.firmys.gameservices.generated.models.Error;
import com.firmys.gameservices.generated.models.ErrorTypes;
import com.firmys.gameservices.generated.models.Operations;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class ErrorUtils {

  private static String dataString(Object object) {
    return Optional.ofNullable(object)
        .filter(d -> CommonObject.class.isAssignableFrom(d.getClass()))
        .map(d -> (CommonObject) d)
        .map(CommonObject::toJson)
        .orElseGet(() -> String.valueOf(object));
  }

  public static ServiceException toException(Error error) {
    return switch (error.type()) {
      case MONGODB -> MongoDBException.create(error);
      case GRAPHQL -> GraphQLException.create(error);
      case WEB -> WebException.create(error);
      case FLAVOR -> FlavorException.create(error);
      case INTERNAL -> InternalException.create(error);
      case OTHER -> ServiceException.create(error);
    };
  }

  public static BiFunction<Exception, ServerHttpRequest, Error> webError() {
    return (exception, request) ->
        ErrorUtils.baseError(
            request.getMethod().name(),
            ErrorTypes.WEB,
            exception.getMessage(),
            400,
            request.getURI().getPath(),
            CommonEntity.class.getTypeName(),
            request.toString());
  }

  public static BiFunction<Throwable, Operations, Error> mongoDbError(
      Class<? extends CommonEntity> entityType, @Nullable Object requestData) {
    return (exception, op) ->
        ErrorUtils.baseError(
            op.name(),
            ErrorTypes.MONGODB,
            exception.getMessage(),
            400,
            entityType.getSimpleName().toLowerCase(),
            entityType.getTypeName(),
            requestData);
  }

  private static Error baseError(
      @NotNull String operation,
      @NotNull ErrorTypes errorType,
      String message,
      int errorCode,
      String path,
      String objectType,
      @Nullable Object requestData) {
    return Error.builder()
        .title(
            String.format(
                "[%s] [%s]: [ERROR] %s", objectType, operation.toUpperCase(), errorType.name()))
        .message(message)
        .path(path)
        .objectType(objectType)
        .type(errorType)
        .errorCode(errorCode)
        .data(orVoid(() -> dataString(requestData)))
        .build();
  }
}
