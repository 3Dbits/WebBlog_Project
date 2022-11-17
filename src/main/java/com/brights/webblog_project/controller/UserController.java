package com.brights.webblog_project.controller;

import com.brights.webblog_project.model.User;
import com.brights.webblog_project.model.UserCredentials;
import com.brights.webblog_project.service.UserCredentialsService;
import com.brights.webblog_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCredentialsService userCredentialsService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/login")
    public String login(Principal principal){
        return "login";
    }

    @GetMapping("/user/register")
    public String register (Model model) {
        model.addAttribute("people", new User());
        model.addAttribute("newUserCred", new UserCredentials());

        return "/user/newRegister";
    }
    @PostMapping("/user/register")
    public String registerStudent(@Valid @ModelAttribute("newUserCred") UserCredentials userCredentials,
                                  BindingResult bindingCred,
                                  @Valid @ModelAttribute("people") User user,
                                  BindingResult bindingUser) {
        if(bindingUser.hasErrors() || bindingCred.hasErrors()){
            return "/user/newRegister";
        }

        try {
            userCredentials.setUser(userService.saveUsers(user));
            userCredentials.setUsername(userCredentials.getUsername());
            userCredentials.setPassword(encoder.encode(userCredentials.getPassword()));
            userCredentials.setRoles("ROLE_USER");
            userCredentialsService.saveUserCredentials(userCredentials);
        } catch (Exception e) {
            return "/user/newRegister";
        }
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String userList(Model model){
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("userCredList", userCredentialsService.getAllUserCred());

        return "/user/listUsers";
    }

    @GetMapping("/users/{id}")
    public String userUpdateForm(Model model, @PathVariable(value = "id") long id){
        model.addAttribute("user", userCredentialsService.getDetailsById(id).getUser());
        model.addAttribute("userCred", userCredentialsService.getDetailsById(id));

        return "/user/updateUser";
    }

    @PostMapping("/users/update")
    public String userUpdate(@Valid @ModelAttribute UserCredentials userCredentials,
                             BindingResult bindingCred,
                             @Valid @ModelAttribute User user,
                             BindingResult bindingUser) {
        if(bindingUser.hasErrors() || bindingCred.hasErrors()){
            return "/user/updateUser";
        }

        try {
            userService.saveUsers(user);
            userCredentialsService.saveUserCredentials(userCredentials);
        } catch (Exception e) {
            return "/user/updateUser";
//            System.err.print("");
        }
        return "redirect:/users";
    }
}
