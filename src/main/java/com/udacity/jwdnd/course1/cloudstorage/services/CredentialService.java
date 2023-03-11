package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

/**
 * @author T.Q
 * @version 1.0
 */
@Service
public class CredentialService {

    @Autowired
    CredentialMapper credentialMapper;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    UserMapper userMapper;

    public ArrayList<Credentials> getCredentialsInfo(int userId) {
        return credentialMapper.getCredentials(userId);
    }

    public void createOrUpdateCredentials(String url, String userName, String password, String id) {
        if (id == null || id.equals("")) {
            Credentials newCredentials = new Credentials();
            newCredentials.setUrl(url);
            newCredentials.setUserName(userName);
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
            newCredentials.setPassword(encryptedPassword);
            newCredentials.setKey(encodedKey);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Integer userId = userMapper.getUser(authentication.getName()).getUserId();
            newCredentials.setUserId(userId);

            credentialMapper.insert(newCredentials);
        } else {
            Credentials credentialsByCredentialId = credentialMapper.getCredentialsByCredentialId(Integer.parseInt(id));
            String encryptedPassword = encryptionService.encryptValue(password, credentialsByCredentialId.getKey());
            credentialMapper.updateCredential(url, userName, encryptedPassword, Integer.valueOf(id));
        }
    }

    public void deleteCredentials(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }


}
