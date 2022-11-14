package com.brights.webblog_project.service;

import com.brights.webblog_project.model.PostComment;
import com.brights.webblog_project.model.User;

import java.util.List;

public interface PostCommentService {

    List<PostComment> getAllPostComments();
    void savePostComment(PostComment postComment);
    PostComment getPostCommentById(long id);
    void deletePostCommentById(long id);
    List<PostComment> getAllPostCommentsByPostId(long id);
}
