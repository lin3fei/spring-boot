package org.eleven.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.eleven.constant.ErrorCode;
import org.eleven.dao.UserMapper;
import org.eleven.exception.MyException;
import org.eleven.model.User;
import org.eleven.util.RedisUtil;
import org.eleven.vo.AuthUser;
import org.eleven.vo.LoginVO;
import org.eleven.vo.MyPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil<AuthUser> redisUtil;

    public int saveUser(User user) {
        return userMapper.insertSelective(user);
    }

    public int removeUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public MyPage<User> findUser(User user) {
        PageInfo<User> pageInfo = PageHelper.startPage(user.getPageNum(), user.getPageSize())
                .doSelectPageInfo(() -> userMapper.select(user));
        MyPage<User> myPage = new MyPage<>();
        BeanUtils.copyProperties(pageInfo, myPage);
        return myPage;
    }

    public AuthUser login(LoginVO loginVO) {
        User user = new User();
        user.setUsername(loginVO.getUsername());
        user.setPassword(loginVO.getPassword());
        user = userMapper.selectOne(user);
        if (user == null) {
            throw new MyException(ErrorCode.LOGIN_PASS_ERROR);
        }
        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(user, authUser);
        String token = UUID.randomUUID().toString();
        authUser.setToken(token);
        redisUtil.set(token, authUser, 1L, TimeUnit.DAYS);
        return authUser;
    }

}
