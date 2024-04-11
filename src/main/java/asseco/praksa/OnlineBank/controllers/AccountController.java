package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.RegistrationRequest;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.InputStream;
import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/api/account")
    public ResponseEntity<Account> getAccountDetails() {
        Optional<Account> account = accountRepository.findById(1L); // Example: get the account with ID 1
        System.out.println(account);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/accountByAccountNumber")
    public ResponseEntity<Account> getAccountDetailsByAccountNumber(@RequestBody String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber); // Example: get the account with account number
        System.out.println(account);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            Account registered = accountService.registerUser(registrationRequest);
            System.out.println("Registered: " + registered);
            return ResponseEntity.ok().body("User registered successfully with ID: " + registered.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
}