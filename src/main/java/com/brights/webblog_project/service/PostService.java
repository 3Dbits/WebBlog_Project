package com.brights.webblog_project.service;

import com.brights.webblog_project.model.Post;
import com.brights.webblog_project.model.User;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();
    void savePost(Post post);
    Post getPostById(long id);
    void deletePostById(long id);
}
