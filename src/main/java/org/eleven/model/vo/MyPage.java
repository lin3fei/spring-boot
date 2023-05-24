package org.eleven.model.vo;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MyPage<T> {

    private Long total = 0L;
    private List<T> list = new ArrayList<>();

    private MyPage() {

    }

    public static <T> MyPage<T> build(Page<T> page) {
        MyPage<T> myPage = new MyPage<>();
        myPage.setTotal(page.getTotal());
        myPage.setList(page.getResult());
        return myPage;
    }
}
