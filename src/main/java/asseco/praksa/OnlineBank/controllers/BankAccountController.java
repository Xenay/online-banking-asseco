package asseco.praksa.OnlineBank.controllers;

import asseco.praksa.OnlineBank.dto.BankAccountDto;
import asseco.praksa.OnlineBank.dto.TransferDto;
import asseco.praksa.OnlineBank.model.BankAccount;
import asseco.praksa.OnlineBank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling bank account related operations such as creating accounts and transferring funds.
 */
@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * Creates a new bank account based on the data provided in the bank account DTO.
     *
     * @param bankAccountDTO the data transfer object containing the necessary information to create a new bank account
     * @return a {@link ResponseEntity} containing the newly created {@link BankAccount}
     */
    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccountDto bankAccountDTO) {
        BankAccount createdAccount = bankAccountService.createBankAccount(bankAccountDTO);
        return ResponseEntity.ok(createdAccount);
    }

    /**
     * Processes a transfer request between two bank accounts based on the transfer DTO.
     *
     * @param transferDto the data transfer object containing the necessary information to process a transfer
     * @return a {@link ResponseEntity} indicating the outcome of the transfer attempt, typically with a message
     *         indicating success if the transfer is processed without issues
     */
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
        bankAccountService.internalTransfer(transferDto);
        return ResponseEntity.ok().body("Transfer successful");
    }
}