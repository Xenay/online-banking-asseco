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

/**
 * Controller for handling requests related to transactional operations.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private PaymentOrderService paymentOrderService;

    /**
     * Retrieves all transactions available in the system.
     *
     * @return a {@link ResponseEntity} containing a list of {@link PaymentOrder}.
     *         The list includes all transactions processed or recorded.
     */
    @GetMapping
    public ResponseEntity<List<PaymentOrder>> getAllTransactions() {
        return ResponseEntity.ok(paymentOrderService.getAllTransactions());
    }

    /**
     * Finds all transactions associated with a specific account ID. This can include
     * transactions where the account is either the sender or the recipient.
     *
     * @param accountId the unique identifier of the account for which to retrieve transactions.
     * @return a {@link ResponseEntity} containing a list of {@link PaymentOrder}
     *         associated with the provided account ID.
     */
    @RequestMapping("/user")
    @GetMapping
    public ResponseEntity<List<PaymentOrder>> findAllByAccountIdOrRecipientId(@RequestParam("accountId") Long accountId ) {
        return ResponseEntity.ok(paymentOrderService.getAllTransactionsById(accountId));
    }
}