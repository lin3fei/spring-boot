package org.eleven.vo;

import lombok.Data;
import org.eleven.constant.ErrorCode;
import org.eleven.exception.MyException;

@Data
public class MyResponse<T> {

    private int code;
    private String msg;
    private T body;

    private MyResponse() {

    }

    private static <T> MyResponse<T> build(int code, String msg, T body) {
        MyResponse<T> myResponse = new MyResponse<>();
        myResponse.setCode(code);
        myResponse.setMsg(msg);
        myResponse.setBody(body);
        return myResponse;
    }

    public static <T> MyResponse<T> ok() {
        return build(0, null, null);
    }

    public static <T> MyResponse<T> ok(T body) {
        return build(0, null, body);
    }

    public static <T> MyResponse<T> error(MyException e) {
        return build(e.getCode(), e.getMessage(), null);
    }

    public static <T> MyResponse<T> error(ErrorCode e) {
        return build(e.getCode(), e.getMessage(), null);
    }

}
