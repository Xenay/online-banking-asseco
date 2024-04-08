package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.PaymentOrder;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.repositories.PaymentOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentOrderService {

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Autowired
    private AccountRepository accountRepository; // Assuming this exists

    @Transactional
    public PaymentOrder createPaymentOrder(PaymentOrder paymentOrder) {
        // Update account balance logic here
        // Example:
       Account account = accountRepository.findById(paymentOrder.getAccount().getId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + paymentOrder.getAccount().getId()));
        account.setBalance(account.getBalance().subtract(paymentOrder.getAmount()));
        paymentOrder.setAccount(account);
        return paymentOrderRepository.save(paymentOrder);
    }
    public List<PaymentOrder> getAllTransactions() {
        // Here you might adjust the amount for outgoing transactions if needed
        return paymentOrderRepository.findAllByOrderByTransactionDateDesc();
    }
}
