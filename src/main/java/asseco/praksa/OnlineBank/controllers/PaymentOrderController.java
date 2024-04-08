package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.PaymentOrderDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.PaymentOrder;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.services.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payment-orders")
public class PaymentOrderController {
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private AccountRepository accountRepository;

    private Long extractAccountIdFromPrincipal(Principal principal) {
        // Your logic to extract the accountId from the principal goes here
        // This is placeholder logic and needs to be replaced
        return Long.valueOf(principal.getName());
    }
    public PaymentOrderController(PaymentOrderService paymentOrderService, AccountRepository accountRepository) {
        this.paymentOrderService = paymentOrderService;
        this.accountRepository = accountRepository;
    }
    @PostMapping
    public ResponseEntity<PaymentOrder> createPaymentOrder(@RequestBody PaymentOrderDto paymentOrderDto) {
        Account account = accountRepository.findById(paymentOrderDto.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        PaymentOrder paymentOrder = new PaymentOrder();
        // Populate the PaymentOrder entity with data from the PaymentOrderDto
        paymentOrder.setRecipientName(paymentOrderDto.getRecipientName());
        paymentOrder.setRecipientIban(paymentOrderDto.getRecipientIban());
        paymentOrder.setAmount(paymentOrderDto.getAmount());
        paymentOrder.setTransactionDate(LocalDate.now()); // Explicitly set it, though this should already be the defaul
        paymentOrder.setPaymentDescription(paymentOrderDto.getPaymentDescription());
        paymentOrder.setAccount(account); // Link the payment order to the account

        PaymentOrder createdOrder = paymentOrderService.createPaymentOrder(paymentOrder);
        return ResponseEntity.ok(createdOrder);
    }

}
