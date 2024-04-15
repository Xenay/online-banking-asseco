package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.model.PaymentOrder;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.repositories.BankAccountRepository;
import asseco.praksa.OnlineBank.repositories.PaymentOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentOrderService {
    @Autowired
    private PaymentOrderRepository paymentOrderRepository;
    @Autowired
    private AccountRepository accountRepository; // Assuming this exists
    @Autowired
    private BankAccountRepository bankAccountRepository; // Assuming this exists
    @Transactional
    public PaymentOrder createPaymentOrder(PaymentOrder paymentOrder) {
        BankAccount fromAccount = bankAccountRepository.findByIBAN(paymentOrder.getSenderIban());
        fromAccount.setBalance(fromAccount.getBalance().subtract(paymentOrder.getAmount()));

        // Add to recipient account balance
        BankAccount toAccount = bankAccountRepository.findByIBAN(paymentOrder.getRecipientIban());
        toAccount.setBalance(toAccount.getBalance().add(paymentOrder.getAmount()));

        return paymentOrderRepository.save(paymentOrder);
    }
    public List<PaymentOrder> getAllTransactions() {
        // Here you might adjust the amount for outgoing transactions if needed
        return paymentOrderRepository.findAllByOrderByTransactionDateDesc();
    }
    public List<PaymentOrder> getAllTransactionsById(Long accountId) {
        // Here you might adjust the amount for outgoing transactions if needed
        return paymentOrderRepository.findAllByAccountIdOrRecipientId(accountId);
    }
    public List<PaymentOrder> filterByPeriod(LocalDate startDate, LocalDate endDate, Long accountId) {
        if (startDate != null && endDate != null) {
            // Assuming a method in the repository that finds by accountId and date range
            return paymentOrderRepository.findByTransactionDateBetweenAndAccountId(startDate, endDate, accountId);
        }
        else {
           return paymentOrderRepository.findAllByAccountIdOrRecipientId(accountId);
        }
    }
    public List<PaymentOrder> filterByType(String payment_type, Long accountId) {
        return paymentOrderRepository.findByPaymentTypeAndAccountId(payment_type, accountId);
    }
    public List<PaymentOrder> filterByRecipient(String recepientName) {
        return paymentOrderRepository.findByRecipientName(recepientName);
    }
    public List<PaymentOrder> filterByAmount(BigDecimal minAmount, BigDecimal maxAmount, Long accountId) {
        // Define default values for comparison to handle when only one value is provided
        BigDecimal defaultMinAmount = BigDecimal.valueOf(Long.MIN_VALUE);
        BigDecimal defaultMaxAmount = BigDecimal.valueOf(Long.MAX_VALUE);

        // Use the provided values or default to the entire range if null
        minAmount = (minAmount != null) ? minAmount : defaultMinAmount;
        maxAmount = (maxAmount != null) ? maxAmount : defaultMaxAmount;

        return paymentOrderRepository.findByAmountBetweenAndAccountId(minAmount, maxAmount, accountId);
    }
}
