package org.eleven.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(0, "请求成功"),
    AUTHORIZE_EXPIRE(4011001, "令牌失效，请重新登录"),
    NO_AUTHORIZE(4011002, "没有操作权限，请联系管理员"),

    LOGIN_PASS_ERROR(4101001, "登录失败，用户名或密码错误"),
    LOGIN_PARAM_ERROR(4101002, "用户名或密码不能为空"),
    OLD_PASSWORD_ERROR(4101003, "旧密码错误"),
    CODE_EXPIRE_ERROR(4101004, "验证码过期"),
    CODE_ERROR(4101005, "验证码错误"),

    USERNAME_OR_EMAIL_EXIST(4102001, "用户名或邮箱已存在"),
    PARAM_EMAIL_NULL(4102002, "邮箱地址不能为空"),
    EMAIL_NOT_EXIST(4102003, "邮箱地址不存在"),
    USER_NOT_EXIST(4102004, "用户不存在"),

    PERMISSION_NAME_EXIST(4103001, "权限名称已存在"),

    ROLE_NOT_EXIST(4104001, "角色不存在"),
    ROLE_NAME_EXIST(4104002, "角色名称已存在"),
    ROLE_USED(4104003, "角色使用中"),

    TASK_NOT_EXIST(4105001, "任务不存在"),

    SERVER_ERROR(5001001, "系统错误，请稍后再试");

    private final int code;
    private final String message;

}
