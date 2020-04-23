package org.eleven.dao;

import java.util.List;

import org.eleven.model.User;
import org.eleven.util.MyMapper;

public interface UserMapper extends MyMapper<User> {

    List<User> listUsers(User user);
    
}
