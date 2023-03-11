package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @author T.Q
 * @version 1.0
 */
@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert (Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    ArrayList<Credentials> getCredentials(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credentials getCredentialsByCredentialId(int credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, userName = #{userName}, password = #{password} WHERE credentialId = #{credentialId}")
    void updateCredential(String url, String userName, String password, Integer credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredential(int credentialId);

}
