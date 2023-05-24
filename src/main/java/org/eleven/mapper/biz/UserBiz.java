package org.eleven.mapper.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.eleven.mapper.UserMapper;
import org.eleven.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lin3fei@126.com
 * @since 2023-05-24
 */
@Component
@RequiredArgsConstructor
public class UserBiz {

    private final UserMapper userMapper;

    public User getByUsernameAndPassword(String username, String password) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    public List<User> selectList(String nickname, Long deptId) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getDeptId, deptId);
        if (StringUtils.hasText(nickname)) {
            wrapper.like(User::getNickname, nickname);
        }
        return userMapper.selectList(wrapper);
    }
}
