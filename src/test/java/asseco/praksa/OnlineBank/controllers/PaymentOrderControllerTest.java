package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.PaymentOrderDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.model.PaymentOrder;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.repositories.BankAccountRepository;
import asseco.praksa.OnlineBank.services.PaymentOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentOrderControllerTest {

    @Mock
    private PaymentOrderService paymentOrderService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private PaymentOrderController paymentOrderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void filterByPeriod_successful() {
        List<PaymentOrder> orders = Arrays.asList(new PaymentOrder(), new PaymentOrder());
        when(paymentOrderService.filterByPeriod(any(), any(), anyLong())).thenReturn(orders);

        ResponseEntity<List<PaymentOrder>> response = paymentOrderController.filterByPeriod(LocalDate.now(), LocalDate.now().plusDays(1), 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Test
    void createPaymentOrder_invalidRecipientIban() {
        PaymentOrderDto dto = new PaymentOrderDto();
        dto.setAccountId(1L);
        dto.setRecipientIban("invalid-iban");

        Account account = new Account();
        when(accountRepository.findById(dto.getAccountId())).thenReturn(Optional.of(account));
        when(bankAccountRepository.findByIBAN(dto.getRecipientIban())).thenReturn(null);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            paymentOrderController.createPaymentOrder(dto);
        });

        assertTrue(exception.getMessage().contains("Invalid recipient IBAN"));
    }
    @Test
    void filterByType_successful() {
        List<PaymentOrder> orders = Arrays.asList(new PaymentOrder(), new PaymentOrder());
        when(paymentOrderService.filterByType(anyString(), anyLong())).thenReturn(orders);

        ResponseEntity<List<PaymentOrder>> response = paymentOrderController.filterByType("Type", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void filterByRecipient_successful() {
        List<PaymentOrder> orders = Arrays.asList(new PaymentOrder(), new PaymentOrder());
        when(paymentOrderService.filterByRecipient(anyString())).thenReturn(orders);

        ResponseEntity<List<PaymentOrder>> response = paymentOrderController.filterByRecipient("Recipient");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void filterByAmount_successful() {
        List<PaymentOrder> orders = Arrays.asList(new PaymentOrder(), new PaymentOrder());
        when(paymentOrderService.filterByAmount(any(), any(), anyLong())).thenReturn(orders);

        ResponseEntity<List<PaymentOrder>> response = paymentOrderController.filterByAmount(Optional.of(new BigDecimal("10.00")), Optional.of(new BigDecimal("100.00")), 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
}
