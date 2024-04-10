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

@RestController
@RequestMapping("/api/user/bank-accounts")
public class UserController {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<List<BankAccountInfoDto>> getBankAccountsForCurrentUser(@RequestParam String username) {
        // Assuming you have a way to get the current user's ID
        Long currentUserId = getCurrentUserId(username);
        System.out.println("Current user ID: " + currentUserId);
        List<BankAccountInfoDto> accounts = bankAccountService.getBankAccountsForUser(currentUserId);
        System.out.println(accounts);
        return ResponseEntity.ok(accounts);
    }

    public Long getCurrentUserId(String username) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Authentication: " + authentication);

//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            System.out.println("Principal class: " + principal.getClass().getName());
//
//            if (principal instanceof UserDetails) {
//                return ((CustomUserDetails) principal).getId();
//            }
//        }

        ;
        System.out.println("Username: " + username);
        Account account = accountRepository.findByUsername(username);
        System.out.println("Account: " + account);
        return account != null ? account.getId() : null;
    }
    }

