package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.LoginRequest;
import asseco.praksa.OnlineBank.services.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthenticationController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        System.out.println("Username: " + loginRequest.getUsername());
        System.out.println("Password: " + loginRequest.getPassword());
        boolean isAuthenticated = accountService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        System.out.println("Is authenticated: " + isAuthenticated);

        if (isAuthenticated) {
            return ResponseEntity.ok().body(Collections.singletonMap("message", "OK"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        }
    }
}
