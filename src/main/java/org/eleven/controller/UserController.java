package org.eleven.controller;

import org.eleven.model.User;
import org.eleven.service.UserService;
import org.eleven.vo.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public MyPage<User> findUser(User user) {
        return userService.findUser(user);
    }
}
