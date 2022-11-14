package com.brights.webblog_project.controller;

import com.brights.webblog_project.model.Post;
import com.brights.webblog_project.model.PostComment;
import com.brights.webblog_project.service.PostCommentService;
import com.brights.webblog_project.service.PostService;
import com.brights.webblog_project.service.UserCredentialsService;
import com.brights.webblog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                               Model model,
                               @RequestParam(required = false) boolean published) {
        if(bindingResult.hasErrors()){
            return "/post/addNew";
        }

        post.setPublished(published);

        postService.savePost(post);

        return "redirect:/";
    }
////////////

//forma za pisanje komentara
    @PostMapping("/post/comment")
    public String saveComment(@Valid @ModelAttribute PostComment postComment,
                              BindingResult bindingComment,
                          /*    @Valid */@ModelAttribute Post post
                             /* BindingResult bindingPost*/)  {
        System.err.println("POST comment: " + postComment); // for testing debugging purposes
        if (bindingComment.hasErrors()/* || bindingPost.hasErrors() */) {
            System.err.println("Comment did not validate");
            return "/post/postCommentForm";
        } else {
            postComment.setPost(postService.getPostById(post.getId()));
            this.postCommentService.savePostComment(postComment);;
            System.err.println("SAVE comment: " + postComment); // for testing debugging purposes
            return "redirect:/post/" + postComment.getPost().getId();
        }
    }

    //kreiramo novi komentar -- spojen na post/Comment
   // njezin submit bi trebao pokrenuti akciju za postMapping post/comment
    @GetMapping("/showCommentForUpdate/{id}") //ovaj id je id od POSTa
    public String commentForm (@PathVariable(value = "id") long id, Model model){

        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("postComment", new PostComment());
        return "/post/postCommentForm";
    }


//vidjeti post po njegovom IDu i sve njegove komentare
    @GetMapping("/post/{id}")
    public String viewPost(Model model, @PathVariable(value = "id")long id) {

        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("postComment", postCommentService.getAllPostCommentsByPostId(id));
        return "/post/postView";
    }




   // @GetMapping("/comment/{id}")



    //////
/*
@GetMapping("/post/{id}/comments")
public String projectsHome(Model model) {
    model.addAttribute("PostComment", PostCommentService.getAllPostComments());
    return "post/comments";
}

    @GetMapping("/comments/showNewComment")
    public String showNewProjectForm (Model model){
        model.addAttribute("postComment", new PostComment());
        model.addAttribute("getAllPostComments", PostCommentService.getAllPostComments());
        return "post/comments/new";
    }

    @PostMapping("/post/comments")
    public String saveProject (@Valid @ModelAttribute PostComment postComment,
                               BindingResult bidingResult,
                               Model model){
        if(bidingResult.hasErrors()){
            model.addAttribute("getAllPostComments", PostCommentService.getAllProjects());
            return "post/comments/new";
        }

        projectService.saveProject(project);
        return "redirect:/projects";
    }*/


}
