package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.PaymentOrderDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.model.PaymentOrder;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.repositories.BankAccountRepository;
import asseco.praksa.OnlineBank.services.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment-orders")
public class PaymentOrderController {
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private AccountRepository accountRepository;
    private final BankAccountRepository bankAccountRepository;

    private Long extractAccountIdFromPrincipal(Principal principal) {
        // Your logic to extract the accountId from the principal goes here
        // This is placeholder logic and needs to be replaced
        return Long.valueOf(principal.getName());
    }
    public PaymentOrderController(PaymentOrderService paymentOrderService, AccountRepository accountRepository,
                                  BankAccountRepository bankAccountRepository) {
        this.paymentOrderService = paymentOrderService;
        this.accountRepository = accountRepository;
        this.bankAccountRepository = bankAccountRepository;
    }
    @PostMapping
    public ResponseEntity<PaymentOrder> createPaymentOrder(@RequestBody PaymentOrderDto paymentOrderDto) throws Exception {
        Account account = accountRepository.findById(paymentOrderDto.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        BankAccount reciever_bank = bankAccountRepository.findByIBAN(paymentOrderDto.getRecipientIban());
        System.out.println(reciever_bank.getAccount().getId());




        PaymentOrder paymentOrder = new PaymentOrder();
        // Populate the PaymentOrder entity with data from the PaymentOrderDto
        paymentOrder.setSenderIban(paymentOrderDto.getSenderIban());
        paymentOrder.setRecipientName(paymentOrderDto.getRecipientName());
        paymentOrder.setRecipientIban(paymentOrderDto.getRecipientIban());
        paymentOrder.setAmount(paymentOrderDto.getAmount());
        paymentOrder.setTransactionDate(LocalDate.now()); // Explicitly set it, though this should already be the defaul
        paymentOrder.setPaymentDescription(paymentOrderDto.getPaymentDescription());
        paymentOrder.setAccount(account); // Link the payment order to the account
        paymentOrder.setRecieverId(reciever_bank.getAccount().getId());
        paymentOrder.setPayment_type(paymentOrderDto.getpaymentType());
        PaymentOrder createdOrder = paymentOrderService.createPaymentOrder(paymentOrder);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/filter/by-period")
    public ResponseEntity<List<PaymentOrder>> filterByPeriod(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("accountId") Long accountId) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByPeriod(startDate, endDate, accountId);
        return ResponseEntity.ok(paymentOrders);
    }
    @GetMapping("/filter/by-type")
    public ResponseEntity<List<PaymentOrder>> filterByType(
            @RequestParam("payment_type") String payment_type,
            @RequestParam("accountId") Long accountId) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByType(payment_type, accountId);
        return ResponseEntity.ok(paymentOrders);
    }
    @GetMapping("/filter/by-sender")
    public ResponseEntity<List<PaymentOrder>> filterByRecipient(
            @RequestParam("recipientName") String recipientName) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByRecipient(recipientName);
        return ResponseEntity.ok(paymentOrders);
    }
    @GetMapping("/filter/by-amount")
    public ResponseEntity<List<PaymentOrder>> filterByAmount(
            @RequestParam("minAmount") Optional<BigDecimal> minAmount,
            @RequestParam("maxAmount") Optional<BigDecimal> maxAmount,
            @RequestParam("accountId") Long accountId) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByAmount(
                minAmount.orElse(null),
                maxAmount.orElse(null),
                accountId);
        return ResponseEntity.ok(paymentOrders);
    }

}
