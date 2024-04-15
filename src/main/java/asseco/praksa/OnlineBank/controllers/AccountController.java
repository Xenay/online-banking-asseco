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

/**
 * Controller responsible for managing account-related operations such as retrieving account details
 * and registering new accounts.
 */
@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    /**
     * Retrieves the details of an account by its ID.
     * Currently hardcoded to retrieve account with ID 1.
     *
     * @return a {@link ResponseEntity} containing the {@link Account} details if found,
     *         otherwise returns a not found response.
     */
    @GetMapping("/api/account")
    public ResponseEntity<Account> getAccountDetails() {
        Optional<Account> account = accountRepository.findById(1L); // Example: get the account with ID 1
        System.out.println(account);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the details of an account by its account number.
     *
     * @param accountNumber the account number used to identify the account.
     * @return a {@link ResponseEntity} containing the {@link Account} details if found,
     *         otherwise returns a not found response.
     */
    @GetMapping("/api/accountByAccountNumber")
    public ResponseEntity<Account> getAccountDetailsByAccountNumber(@RequestBody String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber); // Example: get the account with account number
        System.out.println(account);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Registers a new user based on the provided registration information.
     *
     * @param registrationRequest the registration details provided by the prospective user
     * @return a {@link ResponseEntity} containing a success message with the user ID if registration is successful,
     *         or an error message if registration fails.
     */
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