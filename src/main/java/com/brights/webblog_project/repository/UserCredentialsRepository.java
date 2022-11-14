package com.brights.webblog_project.repository;

import com.brights.webblog_project.model.PostComment;
import com.brights.webblog_project.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    public UserCredentials findByusername(String username);
}
