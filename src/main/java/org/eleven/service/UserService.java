package org.eleven.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.eleven.dao.UserMapper;
import org.eleven.model.User;
import org.eleven.vo.MyPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public int saveUser(User user) {
        return userMapper.insertSelective(user);
    }

    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public MyPage<User> findUser(User user) {
        PageInfo<User> pageInfo = PageHelper.startPage(user.getPageNum(), user.getPageSize()).doSelectPageInfo(() -> userMapper.select(user));
        MyPage<User> myPage = new MyPage<>();
        BeanUtils.copyProperties(pageInfo, myPage);
        return myPage;
    }

}
