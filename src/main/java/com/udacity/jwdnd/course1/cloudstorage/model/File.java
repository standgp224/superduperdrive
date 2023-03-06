package com.udacity.jwdnd.course1.cloudstorage.model;

/**
 * @author T.Q
 * @version 1.0
 */
public class File {
    Integer FileId;
    String fileName;
    String contentType;
    String fileSize;
    Integer userId;
    String fileData;

    public Integer getFileId() {
        return FileId;
    }

    public void setFileId(Integer fileId) {
        FileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}
