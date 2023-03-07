package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author T.Q
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    HashService hashService;

    @Autowired
    UserMapper userMapper;

    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setSalt(encodedSalt);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(user.getUsername());
        user.setPassword(hashedPassword);
        return userMapper.insert(user);
    }

    public boolean findExistedUser(User user) {
        return userMapper.getUser(user.getUsername()) != null;
    }

//    public boolean isValidLogin(User user) {
//        if (findExistedUser(user)) {
//            String hashedPassword = hashService.getHashedValue(user.getPassword(), user.getSalt());
//            return hashedPassword.equals(userMapper.getUser(user.getUsername()).getPassword());
//        } else return false;
//    }

    public User getExistedUserByName(String userName) {
        return userMapper.getUser(userName);
    }

}
