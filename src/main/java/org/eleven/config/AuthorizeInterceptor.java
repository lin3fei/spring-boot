package org.eleven.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eleven.constant.Constants;
import org.eleven.constant.ErrorCode;
import org.eleven.util.MyUtil;
import org.eleven.util.RedisUtil;
import org.eleven.model.vo.AuthUser;
import org.eleven.model.vo.MyResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizeInterceptor implements HandlerInterceptor {

    private final RedisUtil<AuthUser> redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod;
        try {
            handlerMethod = (HandlerMethod) handler;
        } catch (Exception e) {
            return true;
        }
        Class<?> clazz = handlerMethod.getBeanType();
        Authorize annotation = clazz.getAnnotation(Authorize.class);
        // 附加Authorization注解到类、接口(包括注解类型)或enum上面
        if (annotation == null) {
            // 类上面没有，再检查方法上
            Method method = handlerMethod.getMethod();
            annotation = method.getAnnotation(Authorize.class);
        }
        if (annotation != null) {
            log.info("验证权限的的url:{}, method:{}", request.getRequestURI(), request.getMethod());
            String token = request.getHeader(Constants.TOKEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            // 设置允许跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            if (MyUtil.isNullData(token)) {
                response.getWriter().print(MyUtil.toJSONString(MyResult.error(ErrorCode.AUTHORIZE_EXPIRE)));
                return false;
            } else {
                // 验证token
                AuthUser redisUser = redisUtil.get(token);
                if (redisUser == null) {
                    response.getWriter().print(MyUtil.toJSONString(MyResult.error(ErrorCode.AUTHORIZE_EXPIRE)));
                    return false;
                }
                // TODO 验证权限
            }
        }
        return true;
    }

}
