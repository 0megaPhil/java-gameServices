package com.firmys.gameservices.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;

public class JsonUtils {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static <J> J mapJsonToObject(String jsonString, Class<J> jClass) {
        return mapJsonToObject(jsonString, JsonUtils.getMapper().getTypeFactory()
                .constructType(jClass));
    }

    public static <J> J mapJsonToObject(String jsonArrayString, JavaType reference) {
        try {
            return mapper.readValue(jsonArrayString, reference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <J> J mapJsonToObject(String jsonArrayString, TypeReference<J> reference) {
        try {
            return mapper.readValue(jsonArrayString, reference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> mapJsonToMap(String jsonArrayString, JavaType reference,
                                                Class<K> keyClass, Class<V> valueClass) {
        try {
            return mapper.readValue(jsonArrayString, reference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static <O> String writeObjectAsString(O object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
