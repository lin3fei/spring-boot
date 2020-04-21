package org.eleven.vo;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MyModel {

    @Transient
    @JsonIgnore
    private Integer pageNum = 1;

    @Transient
    @JsonIgnore
    private Integer pageSize = 10;

}
