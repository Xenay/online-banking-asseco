package asseco.praksa.OnlineBank.repositories;

import asseco.praksa.OnlineBank.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    List<PaymentOrder> findAllByOrderByTransactionDateDesc();
}
