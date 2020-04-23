package org.eleven.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eleven.constant.ErrorCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public MyException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
