package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class CheckAssertionData {

    @Autowired
    UserRepository userRepository;

    public Boolean check(AssertionData assertionData){
        UserData userData = userRepository.findByUserName(assertionData.getUserName());

        if(!Objects.equals(userData.getCredentialData().getId(), assertionData.getId())){
            return false;
        }
        if(!Objects.equals(userData.getCredentialData().getRawId(), assertionData.getRawId())){
            return false;
        }
        if(!Objects.equals(userData.getCredentialData().getType(), assertionData.getType())){
            return false;
        }
        if(!Objects.equals(userData.getCredentialData().getResponse().getClientDataJSON(), assertionData.getResponse().getClientDataJSON())){
            return false;
        }
        if(!Objects.equals(userData.getCredentialData().getResponse().getAttestationObject(), assertionData.getResponse().getAttestationObject())){
            return false;
        }
        return true;
    }
}
