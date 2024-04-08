package asseco.praksa.OnlineBank.repositories;

import asseco.praksa.OnlineBank.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}