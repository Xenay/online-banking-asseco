package asseco.praksa.OnlineBank.repositories;

import asseco.praksa.OnlineBank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long Id);
    Account findByUsername(String username);
}