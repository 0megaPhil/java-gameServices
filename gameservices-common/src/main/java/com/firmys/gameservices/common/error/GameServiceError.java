package com.firmys.gameservices.common.error;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.CommonObject;
import com.firmys.gameservices.common.JsonUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Setter
@NoArgsConstructor
@RestControllerAdvice
@Accessors(chain = true, fluent = true)
public class GameServiceError<D extends CommonEntity> implements CommonObject {

  private String message;
  private String request;

  public GameServiceError(String message, D request) {
    this.message = message;
    this.request = JsonUtils.toJson(request);
  }

  public GameServiceError(String message, String request) {
    this.message = message;
    this.request = request;
  }
}
