package com.brights.webblog_project.service;

import com.brights.webblog_project.model.Post;
import com.brights.webblog_project.model.PostComment;
import com.brights.webblog_project.repository.PostCommentRepository;
import com.brights.webblog_project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostCommentsServiceImpl implements PostCommentService{

    @Autowired
    private PostCommentRepository postCommentRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public List<PostComment> getAllPostComments() {
        return postCommentRepository.findAll();
    }

    @Override
    public void savePostComment(PostComment postComment) {
        postCommentRepository.save(postComment);
    }

    @Override
    public PostComment getPostCommentById(long id) {
        Optional<PostComment> optional = this.postCommentRepository.findById(id);
        PostComment postComment = null;

        if (optional.isPresent()) {
            postComment = optional.get();
        } else {
            throw new IllegalStateException("Comment with id " + id + " was not found.");
        }

        return postComment;
    }

    @Override
    public void deletePostCommentById(long id) {
        boolean exists = this.postCommentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Post with id " + id + " was not found.");
        }
        this.postCommentRepository.deleteById(id);
    }

    @Override
    public List<PostComment> getAllPostCommentsByPostId(long id) {
        Optional<Post> optional = this.postRepository.findById(id);
        Post post = null;

        if (optional.isPresent()) {
            post = optional.get();
        } else {
            throw new IllegalStateException("Post with id " + id + " was not found.");
        }
        return postCommentRepository.findByPost(post);

    }
}
