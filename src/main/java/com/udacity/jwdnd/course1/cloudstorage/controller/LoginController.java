package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author T.Q
 * @version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getLoginPage(Model model, @ModelAttribute User user) {

        return "login";
    }
}
