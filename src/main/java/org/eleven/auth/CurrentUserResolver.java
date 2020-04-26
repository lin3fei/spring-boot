package org.eleven.auth;

import lombok.extern.slf4j.Slf4j;
import org.eleven.constant.Constants;
import org.eleven.util.RedisUtil;
import org.eleven.vo.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisUtil<AuthUser> redisUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = webRequest.getHeader(Constants.TOKEN);
        if (token != null) {
            log.info("请求的token为:{}", token);
            AuthUser redisUserData = redisUtil.get(token);
            return redisUserData != null ? redisUserData : new AuthUser();
        }
        return new AuthUser();
    }

}
