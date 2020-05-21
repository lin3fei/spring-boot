package org.eleven.service;

import java.util.concurrent.TimeUnit;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.ArithmeticCaptcha;
import org.eleven.constant.ErrorCode;
import org.eleven.dao.UserMapper;
import org.eleven.model.User;
import org.eleven.util.MyUtils;
import org.eleven.util.RedisUtil;
import org.eleven.vo.AuthUser;
import org.eleven.vo.CodeVO;
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
    private RedisUtil<AuthUser> redisAuthUser;

    @Autowired
    private RedisUtil<String> redisString;

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

    public CodeVO getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = MyUtils.uuid();
        // 保存
        redisString.set(uuid, result, 2L, TimeUnit.MINUTES);
        // 验证码信息
        CodeVO code = new CodeVO();
        code.setUuid(uuid);
        code.setImg(captcha.toBase64());
        return code;
    }

    public AuthUser login(LoginVO loginVO) {
        // 查询验证码
        String code = redisString.get(loginVO.getUuid());
        ErrorCode.CODE_EXPIRE_ERROR.hasText(code);
        // 清除验证码
        redisString.delete(loginVO.getUuid());
        ErrorCode.CODE_ERROR.equals(code, loginVO.getCode());
        User user = new User();
        user.setUsername(loginVO.getUsername());
        user.setPassword(loginVO.getPassword());
        user = userMapper.selectOne(user);
        ErrorCode.LOGIN_PASS_ERROR.notNull(user);

        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(user, authUser);
        String token = MyUtils.uuid();
        authUser.setToken(token);
        redisAuthUser.set(token, authUser, 1L, TimeUnit.DAYS);
        return authUser;
    }

}
