package com.firmys.gameservice.common.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firmys.gameservice.common.JsonUtils;
import reactor.util.annotation.Nullable;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonToSetConverter<T> implements AttributeConverter<String, Set<T>> {


    @Override
    public Set<T> convertToDatabaseColumn(@Nullable String attribute) {
        if (attribute == null) {
            return Collections.emptySet();
        }
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(attribute, Set.class);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    @Override
    public String convertToEntityAttribute(Set<T> dbData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(dbData);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
