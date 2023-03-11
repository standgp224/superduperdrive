package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author T.Q
 * @version 1.0
 */
@Service
public class CredentialService {

    @Autowired
    CredentialMapper credentialMapper;

    public ArrayList<Credentials> getCredentialsInfo(int userId) {
        return credentialMapper.getCredentials(userId);
    }

    public void createOrUpdateCredentials(String url, String userName, String password, String id) {
        if (id == null || id.equals("")) {
            Credentials newCredentials = new Credentials();
            newCredentials.setUrl(url);
        }
    }


}
