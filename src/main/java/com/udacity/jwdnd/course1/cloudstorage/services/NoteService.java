package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author T.Q
 * @version 1.0
 */
@Service
public class NoteService {

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    UserMapper userMapper;


    public void createNewNote(String title, String description) {
        Note newNote = new Note();
        newNote.setNoteTitle(title);
        newNote.setNoteDescription(description);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userMapper.getUser(authentication.getName()).getUserId();
        newNote.setUserId(userId);
        noteMapper.insert(newNote);
    }

}
