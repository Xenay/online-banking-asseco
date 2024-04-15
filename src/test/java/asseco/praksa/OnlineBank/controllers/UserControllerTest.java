package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.controllers.UserController;
import asseco.praksa.OnlineBank.dto.BankAccountInfoDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.services.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBankAccountsForCurrentUser_successful() {
        String username = "user";
        Account account = new Account();
        account.setId(1L);  // Assuming setter method for ID
        when(accountRepository.findByUsername(username)).thenReturn(account);

        BankAccountInfoDto dto = new BankAccountInfoDto();  // Assume constructor or setters as needed
        when(bankAccountService.getBankAccountsForUser(1L)).thenReturn(Arrays.asList(dto));

        ResponseEntity<List<BankAccountInfoDto>> response = userController.getBankAccountsForCurrentUser(username);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBankAccountsForCurrentUser_noAccounts() {
        String username = "user";
        Account account = new Account();
        account.setId(1L);
        when(accountRepository.findByUsername(username)).thenReturn(account);
        when(bankAccountService.getBankAccountsForUser(1L)).thenReturn(Arrays.asList());

        ResponseEntity<List<BankAccountInfoDto>> response = userController.getBankAccountsForCurrentUser(username);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBankAccountsForCurrentUser_userNotFound() {
        String username = "nonexistent";
        when(accountRepository.findByUsername(username)).thenReturn(null);

        ResponseEntity<List<BankAccountInfoDto>> response = userController.getBankAccountsForCurrentUser(username);

        assertNotNull(response.getBody());  // Checks if the body is not null
        assertTrue(response.getBody().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatusCode());  // or perhaps NOT_FOUND if you change the method's behavior
    }
}
