package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.dto.RegistrationRequest;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private AccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Account authenticate(String username, String password) {
        Account user = userRepository.findByUsername(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    public Account registerUser(RegistrationRequest registrationRequest) {
        Account newAccount = new Account();
        newAccount.setUsername(registrationRequest.getUsername());
        System.out.println("username: " + registrationRequest.getUsername());
        newAccount.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        System.out.println("password" + newAccount.getPassword());// Encrypting password here
        newAccount.setCurrency("USD");
        Random rand = new Random();
        newAccount.setAccountNumber(registrationRequest.getAccountNumber());
        return userRepository.save(newAccount);
    }
}

