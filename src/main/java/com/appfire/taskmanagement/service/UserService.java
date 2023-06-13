package com.appfire.taskmanagement.service;

import com.appfire.taskmanagement.dto.UserDTO;
import com.appfire.taskmanagement.model.User;

public interface UserService extends Service<User,String> {

    String save(UserDTO userRequest);

    boolean isUsernameValid(String username);

    boolean checkPassword(User user, String password);

    User findByUsername(String username);
    void updateById(String id, User entity);

}
