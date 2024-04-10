package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.LoginRequest;
import asseco.praksa.OnlineBank.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private  JwtTokenProvider tokenProvider;
    @Autowired
    private  AuthenticationManager authenticationManager;

    public LoginController(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Attempt to login with username: {}", loginRequest.getUsername());
            logger.info("Attempt to login with username: {}", loginRequest.getPassword());

            Authentication authentication;
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Ataaaaaaae: {}", loginRequest.getUsername());

            String jwt = tokenProvider.generateToken(authentication);
            logger.info("Login successful for username: {}", loginRequest.getUsername());

            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (BadCredentialsException e) {
            logger.warn("Login attempt failed for username: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        } catch (Exception e) {
            logger.error("An error occurred during the login process for username: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An error occurred during authentication"));
        }
    }
}
