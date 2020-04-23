package org.eleven.controller;

import org.eleven.model.User;
import org.eleven.service.UserService;
import org.eleven.vo.LoginVO;
import org.eleven.vo.MyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public MyResponse<?> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return MyResponse.ok(user.getId());
    }

    @DeleteMapping("/{id}")
    public MyResponse<?> removeUser(@PathVariable Integer id) {
        userService.removeUser(id);
        return MyResponse.ok();
    }

    @PutMapping
    public MyResponse<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return MyResponse.ok();
    }

    @GetMapping("/{id}")
    public MyResponse<?> getUser(@PathVariable Integer id) {
        User user = userService.getUser(id);
        return MyResponse.ok(user);
    }

    @GetMapping
    public MyResponse<?> findUser(User user) {
        return MyResponse.ok(userService.findUser(user));
    }

    @PostMapping("login")
    public MyResponse<?> login(@RequestBody LoginVO loginVO) {
        return MyResponse.ok(userService.login(loginVO));
    }
}
