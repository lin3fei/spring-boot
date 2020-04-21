package org.eleven.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyUtils {

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public static String toJSONString(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("convert to string error", e);
            return "";
        }

    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.error("parse to object error", e);
            return null;
        }
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            JavaType javaType = getObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return getObjectMapper().readValue(json, javaType);
        } catch (IOException e) {
            log.error("parse to list error", e);
            return null;
        }
    }
}
