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
