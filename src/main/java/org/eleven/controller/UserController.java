package org.eleven.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eleven.config.Authorize;
import org.eleven.config.CurrentUser;
import org.eleven.model.User;
import org.eleven.model.dto.LoginDTO;
import org.eleven.model.vo.AuthUser;
import org.eleven.model.vo.MyResult;
import org.eleven.service.UserService;
import org.eleven.util.MyUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Authorize
    public MyResult<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return MyResult.ok(user.getUserId());
    }

    @DeleteMapping("/{id}")
    @Authorize
    public MyResult<?> removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        return MyResult.ok();
    }

    @PutMapping
    @Authorize
    public MyResult<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return MyResult.ok();
    }

    @GetMapping("/{id}")
    @Authorize
    public MyResult<?> getUser(@PathVariable Integer id, @CurrentUser AuthUser authUser) {
        log.info(MyUtil.toJSONString(authUser));
        return MyResult.ok(userService.getUser(id));
    }

    @GetMapping
    @Authorize
    public MyResult<?> findUser(User user) {
        return MyResult.ok(userService.findUser(user));
    }

    @GetMapping("code")
    public MyResult<?> getCode() {
        return MyResult.ok(userService.getCode());
    }

    @PostMapping("login")
    public MyResult<?> login(@RequestBody LoginDTO loginVO) {
        return MyResult.ok(userService.login(loginVO));
    }
}
