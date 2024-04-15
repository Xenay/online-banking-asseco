package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.controllers.BankAccountController;
import asseco.praksa.OnlineBank.dto.BankAccountDto;
import asseco.praksa.OnlineBank.dto.TransferDto;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.services.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountControllerTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBankAccount_successful() {
        BankAccountDto bankAccountDto = new BankAccountDto(); // Assume this has the necessary setters/getters
        BankAccount createdAccount = new BankAccount(); // Assume this has a constructor and setters
        when(bankAccountService.createBankAccount(bankAccountDto)).thenReturn(createdAccount);

        ResponseEntity<BankAccount> response = bankAccountController.createBankAccount(bankAccountDto);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void transfer_successful() {
        TransferDto transferDto = new TransferDto(); // Assume this has the necessary fields and setters
        doNothing().when(bankAccountService).internalTransfer(transferDto);

        ResponseEntity<?> response = bankAccountController.transfer(transferDto);

        assertEquals("Transfer successful", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
