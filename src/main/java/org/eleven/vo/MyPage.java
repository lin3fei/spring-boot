package org.eleven.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MyPage<T> {

    private Long total = 0L;
    private List<T> list = new ArrayList<>();
}
