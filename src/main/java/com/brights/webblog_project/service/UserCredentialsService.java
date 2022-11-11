package com.brights.webblog_project.service;

import com.brights.webblog_project.model.PostComment;
import com.brights.webblog_project.model.UserCredentials;

import java.util.List;

public interface UserCredentialsService {

    List<UserCredentials> getAllUserCredentials();
    void saveUserCredentials(UserCredentials userCredentials);
    UserCredentials getUserCredentialsById(long id);
    void deleteUserCredentialsById(long id);
}
