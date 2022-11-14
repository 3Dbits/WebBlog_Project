package com.brights.webblog_project.service;

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
    public String getUserCredentialsRoles(String username) {
        return userCredentialsRepository.findByusername(username).getRoles();
    }


}
