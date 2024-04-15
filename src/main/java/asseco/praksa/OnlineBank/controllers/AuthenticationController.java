package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.LoginRequest;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.services.AccountService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to handle authentication requests such as login.
 */
@RestController
public class AuthenticationController {
    @Autowired
    private AccountService accountService;

    /**
     * Authenticates a user based on username and password provided through a login request.
     *
     * @param loginRequest the credentials provided by the user for authentication
     * @return a {@link ResponseEntity} with user details if authentication is successful,
     *         or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Account user = accountService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("userId", user.getId());
            response.put("isAuthenticated", true);
            System.out.println(response);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        }
    }
}
