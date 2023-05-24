package org.eleven.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.eleven.constant.ErrorCode;
import org.eleven.mapper.UserMapper;
import org.eleven.mapper.biz.UserBiz;
import org.eleven.model.User;
import org.eleven.model.dto.LoginDTO;
import org.eleven.model.vo.AuthUser;
import org.eleven.model.vo.CodeVO;
import org.eleven.model.vo.MyPage;
import org.eleven.util.MyUtil;
import org.eleven.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserBiz userBiz;

    private final UserMapper userMapper;

    private final RedisUtil<AuthUser> redisAuthUser;

    private final RedisUtil<String> redisString;

    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    public int removeUser(Integer id) {
        return userMapper.deleteById(id);
    }

    public int updateUser(User user) {
        return userMapper.updateById(user);
    }

    public User getUser(Integer id) {
        return userMapper.selectById(id);
    }

    public MyPage<User> findUser(User user) {
        Page<User> page = PageHelper.startPage(1, 10)
                .doSelectPage(() -> userBiz.selectList(user.getNickname(), user.getDeptId()));
        return MyPage.build(page);
    }

    public CodeVO getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = MyUtil.uuid();
        // 保存
        redisString.set(uuid, result, 2L, TimeUnit.MINUTES);
        // 验证码信息
        CodeVO code = new CodeVO();
        code.setUuid(uuid);
        code.setImg(captcha.toBase64());
        return code;
    }

    public AuthUser login(LoginDTO loginDTO) {
        // 查询验证码
        // String code = redisString.get(loginDTO.getUuid());
        // ErrorCode.CODE_EXPIRE_ERROR.hasText(code);
        // 清除验证码
        // redisString.delete(loginDTO.getUuid());
        // ErrorCode.CODE_ERROR.equals(code, loginDTO.getCode());
        User user = userBiz.getByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        ErrorCode.LOGIN_PASS_ERROR.notNull(user);

        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(user, authUser);
        String token = MyUtil.uuid();
        authUser.setToken(token);
        redisAuthUser.set(token, authUser, 1L, TimeUnit.DAYS);
        return authUser;
    }

}
