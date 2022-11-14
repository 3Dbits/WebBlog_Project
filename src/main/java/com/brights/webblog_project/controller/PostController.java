package com.brights.webblog_project.controller;

import com.brights.webblog_project.model.Post;
import com.brights.webblog_project.service.PostCommentService;
import com.brights.webblog_project.service.PostService;
import com.brights.webblog_project.service.UserCredentialsService;
import com.brights.webblog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PostController {

    private UserService userService;
    private UserCredentialsService userCredentialsService;
    private PostService postService;
    private PostCommentService postCommentService;

    @Autowired
    public PostController(UserService userService, UserCredentialsService userCredentialsService, PostService postService, PostCommentService postCommentService) {
        this.userService = userService;
        this.userCredentialsService = userCredentialsService;
        this.postService = postService;
        this.postCommentService = postCommentService;
    }

    @GetMapping("/post/new")
    public String postNewForm(Model model) {
        model.addAttribute("post", new Post());

        return "/post/addNew";
    }

    @PostMapping("/post/new")
    public String postNewSave(@Valid @ModelAttribute Post post,
                               BindingResult bindingResult,
                               @RequestParam(required = false) boolean published) {
        if(bindingResult.hasErrors()){
            return "/post/addNew";
        }

        post.setPublished(published);

        postService.savePost(post);

        return "redirect:/";
    }

//    /
//    /post/{id}
//    /comments/{id}
//
//    post
//    nrenernernenr
//            comments:
//    new comment
}
