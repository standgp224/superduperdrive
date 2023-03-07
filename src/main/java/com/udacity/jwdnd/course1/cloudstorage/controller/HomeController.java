package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author T.Q
 * @version 1.0
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    private int userId;


    @GetMapping()
    public String getHomeView(Authentication auth, Model model) {
        userId = userService.getExistedUserByName(auth.getName()).getUserId();
        model.addAttribute("fileNames", fileService.getFileNames(userId));
        return "home";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String fileName = file.getOriginalFilename();
            String filePath = fileService.uploadFile(file, fileName);
//            List<String> fileNames = (List<String>) model.getAttribute("fileNames");
            if (filePath != null) {
                model.addAttribute("message", "File uploaded successfully: " + filePath);
                model.addAttribute("fileName", fileName);
            } else {
                model.addAttribute("message", "File already exits");
            }
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteUploadedFile() {

        return "redirect:/home";
    }



}
