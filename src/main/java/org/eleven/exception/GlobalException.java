package org.eleven.exception;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.eleven.constant.ErrorCode;
import org.eleven.vo.MyResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(MyException.class)
    public MyResponse<?> handlerBusinessException(HttpServletRequest request, MyException e) {
        handleLog(request, e);
        return MyResponse.error(e);
    }

    /*
     * // Controller上一层相关异常
     * 
     * @ExceptionHandler({NoHandlerFoundException.class,
     * HttpRequestMethodNotSupportedException.class,
     * HttpMediaTypeNotSupportedException.class, MissingPathVariableException.class,
     * MissingServletRequestParameterException.class, TypeMismatchException.class,
     * HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
     * // BindException.class, MethodArgumentNotValidException.class
     * HttpMediaTypeNotAcceptableException.class,
     * ServletRequestBindingException.class, ConversionNotSupportedException.class,
     * MissingServletRequestPartException.class,
     * AsyncRequestTimeoutException.class}) public MyResponse<?>
     * handleServletException(HttpServletRequest request, Exception e) {
     * handleLog(request, e); return
     * MyResponse.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage()); }
     */

    /*
     * // 参数绑定异常
     * 
     * @ExceptionHandler(BindException.class) public MyResponse<?>
     * handleBindException(HttpServletRequest request, BindException e) {
     * handleLog(request, e); return
     * MyResponse.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage()); }
     */

    /*
     * // 参数校验异常，将校验失败的所有异常组合成一条错误信息
     * 
     * @ExceptionHandler(MethodArgumentNotValidException.class) public MyResponse<?>
     * handleValidException(HttpServletRequest request,
     * MethodArgumentNotValidException e) { handleLog(request, e); return
     * MyResponse.error(ErrorCode.SERVER_ERROR.getCode(), e.getMessage()); }
     */

    // 放在最后面，catch其它异常
    @ExceptionHandler(Exception.class)
    public MyResponse<?> handlerException(HttpServletRequest request, Exception e) {
        handleLog(request, e);
        return MyResponse.error(ErrorCode.SERVER_ERROR);
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
