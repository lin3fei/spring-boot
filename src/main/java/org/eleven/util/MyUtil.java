package org.eleven.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyUtil {

    public static boolean isNullData(String... strings) {
        for (String str : strings) {
            if (str == null || "".equals(str.trim())) {
                return true;
            }
        }
        return false;
    }

    public static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public static String toJSONString(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("convert to string error", e);
        }
        return null;

    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            log.error("parse to object error", e);
        }
        return null;
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        try {
            JavaType javaType = getObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return getObjectMapper().readValue(json, javaType);
        } catch (Exception e) {
            log.error("parse to list error", e);
        }
        return Collections.emptyList();
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

}
