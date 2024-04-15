package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Transactional
    public UserDetails loadUserById(Long id) {
        return accountRepository.findById(id).map(user -> new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword())).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
    }
    @Override
    public UserDetails loadUserByUsername(String Id) throws UsernameNotFoundException {
        Account account = accountRepository.findById(Long.valueOf(Id))
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with username: " + Id));

        return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
    }
}