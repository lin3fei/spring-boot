package org.eleven.exception;

import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

public interface Assert extends IErrorCode {

    default void hasText(String... text) {
        for (String str : text) {
            if (!StringUtils.hasText(str)) {
                throw new MyException(this);
            }
        }
    }

    default void isNull(Object obj) {
        if (obj != null) {
            throw new MyException(this);
        }
    }

    default void notNull(Object obj) {
        if (obj == null) {
            throw new MyException(this);
        }
    }

    default void equals(@NonNull String source, String target) {
        if (!source.equalsIgnoreCase(target)) {
            throw new MyException(this);
        }
    }

    default void isEmpty(Collection<?> collection) {
        if (collection != null && !collection.isEmpty()) {
            throw new MyException(this);
        }
    }

    default void notEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new MyException(this);
        }

    }

}
