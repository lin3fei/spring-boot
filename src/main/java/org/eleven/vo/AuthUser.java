package org.eleven.vo;

import java.util.Date;

import lombok.Data;

@Data
public class AuthUser {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phoneNo;
    private String avatar;
    private Integer sex;
    private Boolean status;
    private Integer deptId;
    private Integer jobId;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Integer loginErrorTimes;
    private String token;

}
