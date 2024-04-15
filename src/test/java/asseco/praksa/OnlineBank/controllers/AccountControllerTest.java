package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.controllers.AccountController;
import asseco.praksa.OnlineBank.dto.RegistrationRequest;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountDetails_found() {
        Account account = new Account(); // Assuming Account has a default constructor
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.getAccountDetails();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void getAccountDetails_notFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Account> response = accountController.getAccountDetails();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAccountDetailsByAccountNumber_found() {
        Account account = new Account();
        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(account));

        ResponseEntity<Account> response = accountController.getAccountDetailsByAccountNumber("123456");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    void getAccountDetailsByAccountNumber_notFound() {
        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.empty());

        ResponseEntity<Account> response = accountController.getAccountDetailsByAccountNumber("123456");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void registerUser_success() {
        Account account = new Account(); // Assume Account has an ID setter method
        account.setId(1L);
        when(accountService.registerUser(any())).thenReturn(account);

        RegistrationRequest request = new RegistrationRequest();
        ResponseEntity<?> response = accountController.registerUser(request);

        assertEquals("User registered successfully with ID: 1", response.getBody());
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void registerUser_failure() {
        when(accountService.registerUser(any())).thenThrow(new RuntimeException("Database error"));

        RegistrationRequest request = new RegistrationRequest();
        ResponseEntity<?> response = accountController.registerUser(request);

        assertEquals("Registration failed: Database error", response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
