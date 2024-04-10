package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository userRepository;

    public boolean authenticate(String username, String password) {
        Account user = userRepository.findByUsername(username);
        System.out.println("User: " + user.getPassword() + " aaa " + password);
        System.out.println(user.getPassword().equals(password));
        boolean passwordsMatch = user.getPassword().trim().equals(password.trim());

        return passwordsMatch;
    }
}

