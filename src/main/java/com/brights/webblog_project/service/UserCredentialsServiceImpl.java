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
    public List<UserCredentials> getAllUserCredentials() {
        return userCredentialsRepository.findAll();
    }

    @Override
    public void saveUserCredentials(UserCredentials userCredentials) {
        userCredentialsRepository.save(userCredentials);
    }

    @Override
    public UserCredentials getUserCredentialsById(long id) {
        Optional<UserCredentials> optional = this.userCredentialsRepository.findById(id);
        UserCredentials userCredentials = null;

        if (optional.isPresent()) {
            userCredentials = optional.get();
        } else {
            throw new IllegalStateException("User credentials with id " + id + " was not found.");
        }

        return userCredentials;
    }

    @Override
    public void deleteUserCredentialsById(long id) {
        boolean exists = this.userCredentialsRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("User credentials with id " + id + " was not found.");
        }
        this.userCredentialsRepository.deleteById(id);
    }
}
