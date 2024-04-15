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

/**
 * Handles incoming requests related to payment orders.
 */
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

    /**
     * Creates a new payment order based on the provided payment order details.
     *
     * @param paymentOrderDto the payment order details provided by the client
     * @return a {@link ResponseEntity} containing the newly created {@link PaymentOrder}
     * @throws ResponseStatusException if the account or bank account specified by IBAN is not found
     */
    @PostMapping
    public ResponseEntity<PaymentOrder> createPaymentOrder(@RequestBody PaymentOrderDto paymentOrderDto) throws Exception {

        // Retrieve account or throw if not found
        Account account = accountRepository.findById(paymentOrderDto.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        // Attempt to retrieve the receiver's bank account
        BankAccount reciever_bank = bankAccountRepository.findByIBAN(paymentOrderDto.getRecipientIban());
        if (reciever_bank == null || reciever_bank.getAccount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid recipient IBAN");
        }
        System.out.println(reciever_bank.getAccount().getId());

        // Create and populate the PaymentOrder entity
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

        // Create the payment order using the service
        PaymentOrder createdOrder = paymentOrderService.createPaymentOrder(paymentOrder);
        return ResponseEntity.ok(createdOrder);
    }

    /**
     * Retrieves a list of payment orders filtered by a specific period defined by start and end dates.
     *
     * @param startDate the starting date of the period
     * @param endDate the ending date of the period
     * @param accountId the ID of the account for which to retrieve the payment orders
     * @return a {@link ResponseEntity} containing a list of {@link PaymentOrder} that occurred within the given period
     */
    @GetMapping("/filter/by-period")
    public ResponseEntity<List<PaymentOrder>> filterByPeriod(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("accountId") Long accountId) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByPeriod(startDate, endDate, accountId);
        return ResponseEntity.ok(paymentOrders);
    }

    /**
     * Retrieves a list of payment orders filtered by the type of payment.
     *
     * @param payment_type the type of payment to filter by
     * @param accountId the ID of the account for which to retrieve the payment orders
     * @return a {@link ResponseEntity} containing a list of {@link PaymentOrder} matching the specified type
     */
    @GetMapping("/filter/by-type")
    public ResponseEntity<List<PaymentOrder>> filterByType(
            @RequestParam("payment_type") String payment_type,
            @RequestParam("accountId") Long accountId) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByType(payment_type, accountId);
        return ResponseEntity.ok(paymentOrders);
    }

    /**
     * Retrieves a list of payment orders filtered by the recipient's name.
     *
     * @param recipientName the name of the recipient to filter by
     * @return a {@link ResponseEntity} containing a list of {@link PaymentOrder} matching the specified recipient name
     */
    @GetMapping("/filter/by-sender")
    public ResponseEntity<List<PaymentOrder>> filterByRecipient(
            @RequestParam("recipientName") String recipientName) {
        List<PaymentOrder> paymentOrders = paymentOrderService.filterByRecipient(recipientName);
        return ResponseEntity.ok(paymentOrders);
    }

    /**
     * Retrieves a list of payment orders filtered by a specified amount range.
     *
     * @param minAmount the minimum amount of the range
     * @param maxAmount the maximum amount of the range
     * @param accountId the ID of the account for which to retrieve the payment orders
     * @return a {@link ResponseEntity} containing a list of {@link PaymentOrder} within the specified amount range
     */
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
