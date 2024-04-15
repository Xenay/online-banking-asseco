package asseco.praksa.OnlineBank.controllers;
import asseco.praksa.OnlineBank.dto.BankAccountInfoDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller to manage user bank account interactions.
 */
@RestController
@RequestMapping("/api/user/bank-accounts")
public class UserController {
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Retrieves bank account details associated with a given username.
     *
     * @param username the username to identify the user whose bank accounts are to be retrieved.
     * @return a {@link ResponseEntity} containing a list of {@link BankAccountInfoDto},
     *         representing the bank accounts associated with the user. Returns an empty list
     *         if no accounts are found or the username is not associated with any user.
     */
    @GetMapping
    public ResponseEntity<List<BankAccountInfoDto>> getBankAccountsForCurrentUser(@RequestParam String username) {
        // Assuming you have a way to get the current user's ID
        Long currentUserId = getCurrentUserId(username);
        System.out.println("Current user ID: " + currentUserId);
        List<BankAccountInfoDto> accounts = bankAccountService.getBankAccountsForUser(currentUserId);
        System.out.println(accounts);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Extracts the user ID from the provided username.
     * This method is a stub and requires proper implementation to fetch actual user ID based on authentication context.
     *
     * @param username the username of the user whose ID needs to be extracted.
     * @return the ID of the user associated with the username or null if the username does not correspond to a valid user.
     */
    public Long getCurrentUserId(String username) {
        System.out.println("Username: " + username);
        Account account = accountRepository.findByUsername(username);
        System.out.println("Account: " + account);
        return account != null ? account.getId() : null;
    }
}

