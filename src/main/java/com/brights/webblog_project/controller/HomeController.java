package com.brights.webblog_project.controller;


import com.brights.webblog_project.service.PostCommentService;
import com.brights.webblog_project.service.PostService;
import com.brights.webblog_project.service.UserCredentialsService;
import com.brights.webblog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private UserService userService;
    private UserCredentialsService userCredentialsService;
    private PostService postService;
    private PostCommentService postCommentService;

    @Autowired
    public HomeController(UserService userService, UserCredentialsService userCredentialsService, PostService postService, PostCommentService postCommentService) {
        this.userService = userService;
        this.userCredentialsService = userCredentialsService;
        this.postService = postService;
        this.postCommentService = postCommentService;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("postList", postService.getAllPostsReverseForIndexPage());
        model.addAttribute("commentList", postCommentService.getAllPostComments());

        return "/home/index2";
    }
}
