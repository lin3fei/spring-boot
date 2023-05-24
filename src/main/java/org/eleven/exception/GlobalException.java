package org.eleven.exception;

import lombok.extern.slf4j.Slf4j;
import org.eleven.constant.ErrorCode;
import org.eleven.model.vo.MyResult;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(MyException.class)
    public MyResult<?> handlerBusinessException(HttpServletRequest request, MyException e) {
        handleLog(request, e);
        return MyResult.error(e);
    }

    // Controller上一层相关异常
    @ExceptionHandler({NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class, MissingPathVariableException.class,
            MissingServletRequestParameterException.class, TypeMismatchException.class,
            HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class, ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class})
    public MyResult<?> handleServletException(HttpServletRequest request, Exception e) {
        handleLog(request, e);
        return MyResult.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage());
    }


    // 参数绑定异常
    @ExceptionHandler(BindException.class)
    public MyResult<?> handleBindException(HttpServletRequest request, BindException e) {
        handleLog(request, e);
        return MyResult.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage());
    }

    // 参数校验异常，将校验失败的所有异常组合成一条错误信息
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MyResult<?> handleValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        handleLog(request, e);
        return MyResult.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage());
    }

    // 放在最后面，catch其它异常
    @ExceptionHandler(Exception.class)
    public MyResult<?> handlerException(HttpServletRequest request, Exception e) {
        handleLog(request, e);
        return MyResult.error(ErrorCode.SERVER_ERROR);
    }

    private void handleLog(HttpServletRequest request, Exception e) {
        StringBuilder sb = new StringBuilder();
        if (request != null) {
            sb.append("request method:").append(request.getMethod()).append(", ").append("url:")
                    .append(request.getRequestURL()).append(", ");
        }
        sb.append("exception:").append(e);
        log.error(sb.toString(), e);
    }

}
