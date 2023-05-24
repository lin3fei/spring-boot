package org.eleven.model.vo;

import lombok.Getter;
import lombok.Setter;
import org.eleven.constant.ErrorCode;
import org.eleven.exception.MyException;

@Getter
@Setter
public class MyResult<T> {

    private int code;
    private String msg;
    private T body;

    private MyResult() {

    }

    private static <T> MyResult<T> build(int code, String msg, T body) {
        MyResult<T> myResult = new MyResult<>();
        myResult.setCode(code);
        myResult.setMsg(msg);
        myResult.setBody(body);
        return myResult;
    }

    public static <T> MyResult<T> ok() {
        return build(0, null, null);
    }

    public static <T> MyResult<T> ok(T body) {
        return build(0, null, body);
    }

    public static <T> MyResult<T> error(MyException e) {
        return build(e.getCode(), e.getMessage(), null);
    }

    public static <T> MyResult<T> error(ErrorCode e) {
        return build(e.getCode(), e.getMessage(), null);
    }

    public static <T> MyResult<T> error(int code, String message) {
        return build(code, message, null);
    }

}
