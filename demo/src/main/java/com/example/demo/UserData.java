package com.example.demo;

import lombok.Data;
import javax.persistence.Entity;

@Entity
@Data
public class UserData {
    public String userName;

    private CredentialData credentialData;
}
