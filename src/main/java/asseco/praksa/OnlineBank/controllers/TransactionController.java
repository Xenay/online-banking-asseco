package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.model.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import asseco.praksa.OnlineBank.services.PaymentOrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private PaymentOrderService paymentOrderService;

    @GetMapping
    public ResponseEntity<List<PaymentOrder>> getAllTransactions() {
        return ResponseEntity.ok(paymentOrderService.getAllTransactions());
    }

    @RequestMapping()
    @GetMapping
    public ResponseEntity<List<PaymentOrder>> findAllByAccountIdOrRecipientId(@RequestParam("accountId") Long accountId ) {

        return ResponseEntity.ok(paymentOrderService.getAllTransactionsById(accountId));
    }
}