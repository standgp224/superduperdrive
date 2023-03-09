package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author T.Q
 * @version 1.0
 */
@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert (Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    ArrayList<Note> getNotes(int userId);

    @Select("SELECT noteTitle FROM NOTES WHERE userid = #{userId}")
    String[] getNoteTitle(int userId);

    @Select("SELECT noteDescription FROM NOTES WHERE userid = #{userId}")
    String[] getNoteDescription(int userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void delete(int noteId);



}
