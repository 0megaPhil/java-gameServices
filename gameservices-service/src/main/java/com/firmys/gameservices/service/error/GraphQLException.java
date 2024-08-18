package com.firmys.gameservices.service.error;

import com.firmys.gameservices.generated.models.Error;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GraphQLException extends ServiceException {

  private final Error serviceError;

  public GraphQLException(Error serviceError) {
    super(serviceError);
    this.serviceError = serviceError;
  }

  public static GraphQLException create(Error serviceError) {
    return new GraphQLException(serviceError);
  }

  @Override
  public String getMessage() {
    return serviceError.toJson();
  }
}
