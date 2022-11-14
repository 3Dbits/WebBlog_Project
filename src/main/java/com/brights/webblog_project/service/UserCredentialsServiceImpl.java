package com.brights.webblog_project.service;

import com.brights.webblog_project.model.User;
import com.brights.webblog_project.model.UserCredentials;
import com.brights.webblog_project.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserCredentials saveUserCredentials(UserCredentials userCredentials) {
        return userCredentialsRepository.save(userCredentials);
    }

    @Override
    public UserCredentials getDetails(String username) {
        return userCredentialsRepository.findByusername(username);
    }

    @Override
    public UserCredentials getDetailsById(long id) {
        Optional<UserCredentials> optional = this.userCredentialsRepository.findById(id);
        UserCredentials userCredentials = null;

        if (optional.isPresent()) {
            userCredentials = optional.get();
        } else {
            throw new IllegalStateException("User with id " + id + " was not found.");
        }

        return userCredentials;
    }

    @Override
    public String getUserCredentialsRoles(String username) {
        return userCredentialsRepository.findByusername(username).getRoles();
    }

    @Override
    public List<UserCredentials> getAllUserCred() {
        return userCredentialsRepository.findAll();
    }


}
