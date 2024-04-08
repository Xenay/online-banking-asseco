package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.model.PaymentOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import asseco.praksa.OnlineBank.services.PaymentOrderService;

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
}