package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.dto.BankAccountDto;
import asseco.praksa.OnlineBank.dto.BankAccountInfoDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountRepository accountRepository; // Ensure you have this repository

    public BankAccount createBankAccount(BankAccountDto bankAccountDto) {
        Account account = accountRepository.findById(bankAccountDto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + bankAccountDto.getAccountId()));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(bankAccountDto.getName());
        bankAccount.setType(BankAccount.AccountType.valueOf(bankAccountDto.getType()));
        bankAccount.setMinimumBalance(bankAccountDto.getMinimumBalance());
        bankAccount.setBalance(bankAccountDto.getMinimumBalance());
        bankAccount.setAccount(account); // Set the fetched Account

        return bankAccountRepository.save(bankAccount);
    }
    public List<BankAccountInfoDto> getBankAccountsForUser(Long id) {

        if (id != null) {
            List<BankAccount> accounts = bankAccountRepository.findByAccountId(id);
            return accounts.stream()
                    .map(account -> new BankAccountInfoDto(account.getName(), account.getBalance(), account.getType()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
