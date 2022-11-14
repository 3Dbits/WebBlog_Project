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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCredentialsService userCredentialsService;
    @Autowired
    private PasswordEncoder encoder;

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
        return "redirect:/";
    }

    @GetMapping("/users")
    public String userList(Model model){
        return "/listUsers";
    }
}
