package com.brights.webblog_project.service;

import com.brights.webblog_project.model.User;
import com.brights.webblog_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUsers(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> optional = this.userRepository.findById(id);
        User user = null;

        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new IllegalStateException("User with id " + id + " was not found.");
        }

        return user;
    }

    @Override
    public void deleteUserById(long id) {

    }
}
