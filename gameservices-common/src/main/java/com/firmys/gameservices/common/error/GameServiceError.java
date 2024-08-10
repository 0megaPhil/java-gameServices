package com.firmys.gameservices.common.error;

import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.firmys.gameservices.generated.models.CommonEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Setter
@NoArgsConstructor
@RestControllerAdvice
@Accessors(chain = true, fluent = true)
public class GameServiceError<D extends CommonEntity> {

  private String message;
  private String request;

  public GameServiceError(String message, D request) {
    this.message = message;
    this.request = JSON.toJson(request);
  }

  public GameServiceError(String message, String request) {
    this.message = message;
    this.request = request;
  }
}
