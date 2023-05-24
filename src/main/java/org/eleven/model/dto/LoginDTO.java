package org.eleven.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String uuid;
    private String code;
    private String username;
    private String password;

}
