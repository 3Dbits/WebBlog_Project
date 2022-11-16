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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class PostController {

    private UserService userService;
    private UserCredentialsService userCredentialsService;
    private PostService postService;
    private PostCommentService postCommentService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads";

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
                              @RequestParam(required = false) boolean published,
                              @RequestParam(value = "image", required = false) MultipartFile file,
                              Principal principal) throws IOException {
        if(bindingResult.hasErrors()){
            return "/post/addNew";
        }
        if (post.getPublishedAt() == null) {
            post.setPublishedAt(LocalDate.now());
        }
        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            post.setPathOfPicture("/uploads/" + file.getOriginalFilename());
        }
        post.setPublished(published);
        post.setUser(userService.getUserById(userCredentialsService.getDetails(principal.getName()).getId()));

        postService.savePost(post);

        return "redirect:/";
    }
////////////

//forma za pisanje komentara
    @PostMapping("/post/comment")
    public String saveComment(@Valid @ModelAttribute PostComment postComment,
                              BindingResult bindingComment,
                              @RequestParam(value = "idpost") long id,
                              @RequestParam(value = "username") String username)  {
        System.err.println("POST comment: " + postComment);
        if (bindingComment.hasErrors()) {
            System.err.println("Comment did not validate");
            return "/post/postCommentForm";
        } else {
            postComment.setPost(postService.getPostById(id));
            postComment.setUser(userService.getUserById(userCredentialsService.getDetails(username).getUser().getId()));
            this.postCommentService.savePostComment(postComment);
            System.err.println("SAVE comment: " + postComment);
            return "redirect:/post/" + postComment.getPost().getId();
        }
    }

    //kreiramo novi komentar -- spojen na post/Comment
   // njezin submit bi trebao pokrenuti akciju za postMapping post/comment
    @GetMapping("/showCommentForUpdate/{id}") //ovaj id je id od POSTa
    public String commentForm(@PathVariable(value = "id") long id, Model model, Principal principal){

        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("postComment", new PostComment());
        model.addAttribute("username", principal.getName());
        return "/post/postCommentForm";
    }


//vidjeti post po njegovom IDu i sve njegove komentare
    @GetMapping("/post/{id}")
    public String viewPost(Model model,
                           @PathVariable(value = "id")long id,
                           Principal principal) {

        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("postComment", postCommentService.getAllPostCommentsByPostId(id));
        model.addAttribute("userCred", userCredentialsService.getDetails(principal.getName()));
        model.addAttribute("newComment", new PostComment());
        return "/post/postView";
    }


/////////// reg user brisanje svojih commentara
@GetMapping("/showFormForDelete/{id}")
public String deleteComment(@Valid @ModelAttribute PostComment postComment,
                            BindingResult bindingComment,
                            Principal principal ) {

    if (principal.getName() == null ||
            (userCredentialsService.getUserCredentialsRoles(principal.getName()).equals("ROLE_USER")) &&
                    !principal.getName().equals(userCredentialsService.GetByUser(postComment.getUser()).getUsername())) {
        System.err.println("Forbidden role , not able to delete");
        return "redirect:/";

    } else {
        long number = postCommentService.getPostCommentById(postComment.getId()).getPost().getId();
        postCommentService.deletePostCommentById(postComment.getId());
        return "redirect:/post/" + number;
    }
}

    @GetMapping("/showFormForPostDelete/{id}")
    public String deletePost(@ModelAttribute Post post,
                             @PathVariable(value = "id") long id){

            postService.deletePostById(id);
            return "redirect:/";
    }

    @GetMapping("/postList")
    public String postList(Model model){
        model.addAttribute("postList", postService.getAllPosts());
        return "/post/listPost";
    }

////Update Post


    @GetMapping("/updatePost/{id}")
    public String updateNewForm(Model model,
                                @PathVariable(value="id") long id) {
        model.addAttribute("post", postService.getPostById(id));

        return "/post/updatePost";
    }


    @PostMapping("/updatePost/{id}")
    public String updatePost(/*@Valid*/ @ModelAttribute Post post,
                             BindingResult bindingResult,
                             Model model,
                            @PathVariable(value="id") long id)  {
        if(bindingResult.hasErrors()){

            return "/post/updatePost";
        }
        Post postOld = postService.getPostById(id);
        postOld.setTitle(post.getTitle());
        postOld.setMetaTitle(post.getMetaTitle());
        postOld.setSummary(post.getSummary());
        postOld.setContent(post.getContent());
        postService.savePost(postOld);

        return "redirect:/";
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
