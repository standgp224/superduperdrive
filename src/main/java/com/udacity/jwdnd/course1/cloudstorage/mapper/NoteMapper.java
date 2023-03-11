package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

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

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(int noteId);

    @Select("SELECT noteTitle FROM NOTES WHERE userid = #{userId}")
    String[] getNoteDescription(int userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void delete(int noteId);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    void updateNote(String noteTitle, String noteDescription, Integer noteId);


}
