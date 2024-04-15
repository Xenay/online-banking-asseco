package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.controllers.TransactionController;
import asseco.praksa.OnlineBank.model.PaymentOrder;
import asseco.praksa.OnlineBank.services.PaymentOrderService;
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

class TransactionControllerTest {

    @Mock
    private PaymentOrderService paymentOrderService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTransactions() {
        PaymentOrder paymentOrder1 = new PaymentOrder(); // Assume PaymentOrder has a default constructor
        PaymentOrder paymentOrder2 = new PaymentOrder();
        List<PaymentOrder> paymentOrders = Arrays.asList(paymentOrder1, paymentOrder2);
        when(paymentOrderService.getAllTransactions()).thenReturn(paymentOrders);

        ResponseEntity<List<PaymentOrder>> response = transactionController.getAllTransactions();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findAllByAccountIdOrRecipientId() {
        PaymentOrder paymentOrder1 = new PaymentOrder();
        PaymentOrder paymentOrder2 = new PaymentOrder();
        List<PaymentOrder> paymentOrders = Arrays.asList(paymentOrder1, paymentOrder2);
        when(paymentOrderService.getAllTransactionsById(anyLong())).thenReturn(paymentOrders);

        ResponseEntity<List<PaymentOrder>> response = transactionController.findAllByAccountIdOrRecipientId(1L);

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
