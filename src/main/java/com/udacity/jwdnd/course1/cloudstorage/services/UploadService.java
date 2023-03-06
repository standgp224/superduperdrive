package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author T.Q
 * @version 1.0
 */
@Service
public class UploadService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    UserService userService;


    @Value("${file.upload.directory}")
    private String uploadDirectory;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        byte[] fileBytes = file.getBytes();
        Files.write(filePath, fileBytes);
        File newFile = new File();
        newFile.setFileName(fileName);
        newFile.setFileSize(String.valueOf(file.getSize()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        newFile.setUserId(userService.getExistedUserByName(userName).getUserId());
        return filePath.toString();
    }
}
