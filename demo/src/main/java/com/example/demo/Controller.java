package com.example.demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;;
import java.util.Date;

@RestController
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CheckAssertionData checkAssertionData;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CredentialData credentialData) {

        UserData user = new UserData();
        user.setUserName(credentialData.getUserName());
        user.setCredentialData(credentialData);
        userRepository.save(user);
        return ResponseEntity.ok("Registration successful!");
    }

    @GetMapping("/getCredentials")
    public ResponseEntity<CredentialData> getCredentials(@RequestParam String username) {
        // Retrieve the stored credential data for the specified user from the database
        CredentialData credentialData = userRepository.findByUserName(username).getCredentialData();
        if (credentialData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(credentialData);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AssertionData assertionData) {
        // Verify the assertion data and authenticate the user
        if (checkAssertionData.check(assertionData)) {
            // Authentication successful
            String token = Jwts.builder().setSubject(assertionData.getUserName()).
                    setExpiration(new Date(System.currentTimeMillis() +864000000)).
                    signWith(SignatureAlgorithm.HS512,"jf9i4jgu83nfl0").compact();
            return ResponseEntity.ok(token);
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
        }
    }

}
