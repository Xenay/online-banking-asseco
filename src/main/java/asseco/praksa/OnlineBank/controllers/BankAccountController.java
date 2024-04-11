package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.BankAccountDto;
import asseco.praksa.OnlineBank.dto.TransferDto;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;



    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccountDto bankAccountDTO) {
        BankAccount createdAccount = bankAccountService.createBankAccount(bankAccountDTO);
        return ResponseEntity.ok(createdAccount);
    }
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
        bankAccountService.internalTransfer(transferDto);
        return ResponseEntity.ok().body("Transfer successful");
    }
}