package com.firmys.gameservices.data.conversion;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

public class DataJsonConverters {

  public static class IdJsonSerializer extends JsonSerializer<ObjectId> {

    @Override
    @SneakyThrows
    public void serialize(
        ObjectId id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
      jsonGenerator.writeString(id.toHexString());
    }
  }

  public static class IdJsonDeserializer extends JsonDeserializer<ObjectId> {

    @Override
    @SneakyThrows
    public ObjectId deserialize(
        JsonParser jsonParser, DeserializationContext deserializationContext) {
      String hexString = jsonParser.getValueAsString();
      return new ObjectId(hexString);
    }
  }
}
