package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @author T.Q
 * @version 1.0
 */
@Mapper
public interface CredentialMapper {


    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    ArrayList<Credentials> getCredentials(int userId);

}
