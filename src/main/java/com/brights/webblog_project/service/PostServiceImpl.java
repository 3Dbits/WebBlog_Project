package com.brights.webblog_project.service;

import com.brights.webblog_project.model.Post;
import com.brights.webblog_project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPostsForIndexPage() {
        return postRepository.findAll().stream()
                .filter(Post::isPublished)
                .filter(date -> date.getPublishedAt().isBefore(LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostsReverseForIndexPage() {
        return postRepository.findAll().stream()
                .filter(Post::isPublished)
                .filter(date -> date.getPublishedAt().isBefore(LocalDate.now().plusDays(1)))
                .sorted(Comparator.comparing(Post::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> optional = this.postRepository.findById(id);
        Post post = null;

        if (optional.isPresent()) {
            post = optional.get();
        } else {
            throw new IllegalStateException("Post with id " + id + " was not found.");
        }

        return post;
    }

    @Override
    public void deletePostById(long id) {
        boolean exists = this.postRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Post with id " + id + " was not found.");
        }
        this.postRepository.deleteById(id);
    }
}
