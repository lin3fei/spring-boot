package org.eleven.auth;

import lombok.extern.slf4j.Slf4j;
import org.eleven.constant.Constants;
import org.eleven.constant.ErrorCode;
import org.eleven.util.MyUtils;
import org.eleven.util.RedisUtil;
import org.eleven.vo.AuthUser;
import org.eleven.vo.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil<AuthUser> redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod;
        try {
            handlerMethod = (HandlerMethod) handler;
        } catch (Exception e) {
            return true;
        }
        Class<?> clazz = handlerMethod.getBeanType();
        Authorization annotation = clazz.getAnnotation(Authorization.class);
        // 附加Authorization注解到类、接口(包括注解类型)或enum上面
        if (annotation == null) {
            // 类上面没有，再检查方法上
            Method method = handlerMethod.getMethod();
            annotation = method.getAnnotation(Authorization.class);
        }
        if (annotation != null) {
            log.info("验证权限的的url:{}, method:{}", request.getRequestURI(), request.getMethod());
            String token = request.getHeader(Constants.TOKEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            // 设置允许跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            if (MyUtils.isNullData(token)) {
                response.getWriter().print(MyUtils.toJSONString(MyResponse.error(ErrorCode.AUTHORIZE_EXPIRE)));
                return false;
            } else {
                // 验证token
                AuthUser redisUser = redisUtil.get(token);
                if (redisUser == null) {
                    response.getWriter().print(MyUtils.toJSONString(MyResponse.error(ErrorCode.AUTHORIZE_EXPIRE)));
                    return false;
                }
                // TODO 验证权限
            }
        }
        return true;
    }

}
