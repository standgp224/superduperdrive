package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author T.Q
 * @version 1.0
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String fileName = file.getOriginalFilename();
            String filePath = uploadService.uploadFile(file, fileName);
            model.addAttribute("message", "File uploaded successfully: " + filePath);
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
        return "upload-result";
    }
}
