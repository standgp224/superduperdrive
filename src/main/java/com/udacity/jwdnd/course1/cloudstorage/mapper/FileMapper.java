package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

/**
 * @author T.Q
 * @version 1.0
 */
@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert (File file);

    @Select("SELECT * FROM FILES WHERE fileName = #{fileName}")
    File getFile(String fileName);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    String[] getFileNames(int userId);

    @Delete("DELETE FROM FILES WHERE fileName = #{fileName}")
    void delete(String fileName);

}