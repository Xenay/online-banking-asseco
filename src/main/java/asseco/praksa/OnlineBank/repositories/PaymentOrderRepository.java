package asseco.praksa.OnlineBank.repositories;

import asseco.praksa.OnlineBank.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    List<PaymentOrder> findAllByOrderByTransactionDateDesc();
    @Query("SELECT po FROM PaymentOrder po WHERE po.account.id = :accountId OR po.reciever = :accountId ORDER BY po.transactionDate DESC")
    List<PaymentOrder> findAllByAccountIdOrRecipientId(Long accountId);

    List<PaymentOrder> findByTransactionDateBetweenAndAccountId(LocalDate startDate, LocalDate endDate, Long accountId);
    List<PaymentOrder> findByPaymentTypeAndAccountId(String paymenType, Long accountId);
    List<PaymentOrder> findByRecipientName(String recipientName);
    List<PaymentOrder> findByAmountBetweenAndAccountId(BigDecimal minAmount, BigDecimal maxAmount, Long accountId);





}
