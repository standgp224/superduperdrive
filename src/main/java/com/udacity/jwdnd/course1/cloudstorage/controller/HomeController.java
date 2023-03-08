package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model, Authentication auth) {
        try {
            String fileName = file.getOriginalFilename();
            String filePath = fileService.uploadFile(file, fileName);

            if (filePath != null) {
                model.addAttribute("message", "File uploaded successfully: " + filePath);
            } else {
                model.addAttribute("message", "File already exists");
            }
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        userId = userService.getExistedUserByName(auth.getName()).getUserId();
        model.addAttribute("fileNames", fileService.getFileNames(userId));
        return "home";
    }

    @PostMapping("/delete")
    public String deleteUploadedFile(@RequestParam("fileName") String fileName) {
        fileService.deleteFileByName(fileName);
        return "redirect:/home";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName){
        File file = fileService.getFileByName(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }



}
