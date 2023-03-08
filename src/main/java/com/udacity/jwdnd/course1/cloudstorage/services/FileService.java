package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
public class FileService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    UserService userService;


    @Value("${file.upload.directory}")
    private String uploadDirectory;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        //check if the file exits already
        if (fileMapper.getFile(fileName) != null) {
            return null;
        } else {
            //upload file
            Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            byte[] fileBytes = file.getBytes();
            Files.write(filePath, fileBytes);

            //save file detail into db
            File newFile = new File();
            newFile.setFileName(fileName);
            newFile.setFileSize(file.getSize());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            newFile.setUserId(userService.getExistedUserByName(userName).getUserId());
            newFile.setFileData(fileBytes);
            newFile.setContentType(file.getContentType());
            fileMapper.insert(newFile);

            return filePath.toString();
        }
    }

    public String[] getFileNames(int userId) {
        return fileMapper.getFileNames(userId);
    }

    public void deleteFileByName(String fileName) {
        fileMapper.delete(fileName);
    }

    public File getFileByName(String fileName) {return fileMapper.getFile(fileName);}




}
