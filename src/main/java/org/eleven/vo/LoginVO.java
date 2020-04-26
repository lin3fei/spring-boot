package org.eleven.vo;

import lombok.Data;

@Data
public class LoginVO {

    private String uuid;
    private String code;
    private String username;
    private String password;

}
