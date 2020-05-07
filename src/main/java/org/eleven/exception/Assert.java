package org.eleven.exception;

import org.springframework.util.StringUtils;

public interface Assert {

    MyException newException();

    MyException newException(Object... args);

    default void notNull(Object obj) {
        if (obj == null) {
            throw newException(obj);
        }
    }

    default void hasLength(String text) {
        if (!StringUtils.hasLength(text)) {
            throw newException();
        }
    }

    default void equals(String source, String target) {
        hasLength(source);
        if (!source.equalsIgnoreCase(target)) {
            throw newException();
        }
    }

}
