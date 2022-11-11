package com.brights.webblog_project.service;

import com.brights.webblog_project.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUsers(User user);
    User getUserById(long id);
    void deleteUserById(long id);
}
