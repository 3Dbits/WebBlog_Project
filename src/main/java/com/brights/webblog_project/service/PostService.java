package com.brights.webblog_project.service;

import com.brights.webblog_project.model.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPostsForIndexPage();
    List<Post> getAllPosts();
    List<Post> getAllPostsReverseForIndexPage();
    void savePost(Post post);
    Post getPostById(long id);
    void deletePostById(long id);
}
