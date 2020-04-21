package org.eleven.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eleven.vo.MyModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends MyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
