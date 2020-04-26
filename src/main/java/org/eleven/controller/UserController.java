package org.eleven.controller;

import lombok.extern.slf4j.Slf4j;
import org.eleven.auth.Authorization;
import org.eleven.auth.CurrentUser;
import org.eleven.model.User;
import org.eleven.service.UserService;
import org.eleven.util.MyUtils;
import org.eleven.vo.AuthUser;
import org.eleven.vo.LoginVO;
import org.eleven.vo.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Authorization
    public MyResponse<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return MyResponse.ok(user.getId());
    }

    @DeleteMapping("/{id}")
    @Authorization
    public MyResponse<?> removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        return MyResponse.ok();
    }

    @PutMapping
    @Authorization
    public MyResponse<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return MyResponse.ok();
    }

    @GetMapping("/{id}")
    @Authorization
    public MyResponse<?> getUser(@PathVariable Integer id, @CurrentUser AuthUser authUser) {
        log.info(MyUtils.toJSONString(authUser));
        User user = userService.getUser(id);
        return MyResponse.ok(user);
    }

    @GetMapping
    @Authorization
    public MyResponse<?> findUser(User user) {
        return MyResponse.ok(userService.findUser(user));
    }

    @GetMapping("code")
    public MyResponse<?> getCode() {
        return MyResponse.ok(userService.getCode());
    }

    @PostMapping("login")
    public MyResponse<?> login(@RequestBody LoginVO loginVO) {
        return MyResponse.ok(userService.login(loginVO));
    }
}
