package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author T.Q
 * @version 1.0
 */
@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping()
    public String signup(Model model, @ModelAttribute User user) {
        if (userService.findExistedUser(user)) {
            model.addAttribute("errorMessage", true);
        } else {
            int userCreatedId = userService.createUser(user);
            if (userCreatedId > 0) {
                model.addAttribute("validSignup", true);
            } else model.addAttribute("errorMessage", true);
        }
        return "signup";
    }

}
