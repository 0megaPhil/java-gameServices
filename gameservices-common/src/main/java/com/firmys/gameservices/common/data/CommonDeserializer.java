package com.firmys.gameservices.common.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.firmys.gameservices.generated.models.CommonObject;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

// @Component
@RequiredArgsConstructor
public class CommonDeserializer<E extends CommonObject> extends JsonDeserializer<E> {

  private final Class<E> objectClass;

  @Override
  public E deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    return jsonParser.readValueAs(objectClass);
  }
}
