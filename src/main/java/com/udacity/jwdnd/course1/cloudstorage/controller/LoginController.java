package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author T.Q
 * @version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout, Model model, RedirectAttributes redirectAttributes) {
        if (error != null) {
            redirectAttributes.addFlashAttribute("validLogin", true);
            return "redirect:/login";
        }
        if (logout != null) {
            redirectAttributes.addFlashAttribute("loggedOut", true);
            return "redirect:/login";
        }
        return "login";
    }
//
//    @PostMapping String _login(Model model, @RequestParam("username") String username,
//                               @RequestParam("password") String password) {
//
//    }


}
