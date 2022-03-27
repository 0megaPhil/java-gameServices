package com.firmys.gameservice.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static <J> J mapJsonToObject(String jsonArrayString, JavaType reference) {
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

    //    public static <T> T constructJavaType() {
//
//    }



}
