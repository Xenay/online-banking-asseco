package asseco.praksa.OnlineBank.services;

import asseco.praksa.OnlineBank.dto.BankAccountDto;
import asseco.praksa.OnlineBank.model.Account;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.repositories.AccountRepository;
import asseco.praksa.OnlineBank.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        bankAccount.setAccount(account); // Set the fetched Account

        return bankAccountRepository.save(bankAccount);
    }
}
