package de.ait.javalessons.controller;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseEntity.ok(bankAccounts);
    }

    @PostMapping
    ResponseEntity<BankAccount> createBankAccount(@RequestParam String accountNumber,
                                                  @RequestParam String ownerName) {
        BankAccount bankAccount = new BankAccount(accountNumber, ownerName, 0.0);
        BankAccount saveBankAccount = bankAccountService.saveNewBankAccount(bankAccount);
        if (saveBankAccount == null) {
            log.error("Save bank account failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        log.info("Save bank account with id: {}", saveBankAccount.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saveBankAccount);
    }

    @GetMapping("/{id}")
    ResponseEntity<BankAccount> getBankAccountById(@PathVariable Long id) {
        log.info("Find bank account by id: {}", id);
        return bankAccountService.findBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/{id}/deposit")
    ResponseEntity<Double> deposit(@PathVariable Long id, @RequestParam Double amount) {
        log.info("Depositing {} to bank account with id: {}",amount, id);
        return ResponseEntity.ok(bankAccountService.deposit(amount, id));
    }

    @PostMapping("/{id}/withdraw")
    ResponseEntity<Double> withdraw(@PathVariable Long id, @RequestParam Double amount) {
        log.info("Withdrawing {} from bank account with id: {}",amount, id);
        return ResponseEntity.ok(bankAccountService.withdraw(amount, id));
    }
}
