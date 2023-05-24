package org.eleven.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.eleven.constant.ErrorCode;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;

    public MyException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public MyException(IErrorCode responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

}
