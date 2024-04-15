package asseco.praksa.OnlineBank.repositories;

import asseco.praksa.OnlineBank.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("SELECT b FROM BankAccount b WHERE b.account.id = :accountId")
    List<BankAccount> findByAccountId(@Param("accountId") Long accountId);
    @Query("SELECT b FROM BankAccount b WHERE b.IBAN = :iban")
    BankAccount findByIBAN(@Param("iban") String iban);
}