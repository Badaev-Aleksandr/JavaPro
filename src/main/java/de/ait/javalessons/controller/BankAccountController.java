package de.ait.javalessons.controller;

import de.ait.javalessons.errors.BankAccountBalancePositiveException;
import de.ait.javalessons.errors.BankAccountNotFoundException;
import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        try {
            BankAccount bankAccount = bankAccountService.findBankAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
        } catch (BankAccountNotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{id}/deposit")
    ResponseEntity<Double> deposit(@PathVariable Long id, @RequestParam Double amount) {
        log.info("Depositing {} to bank account with id: {}", amount, id);
        try {
            double deposit = bankAccountService.deposit(amount, id);
            return ResponseEntity.status(HttpStatus.OK).body(deposit);
        } catch (BankAccountNotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException exception) {
            log.error("Deposit failed. {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{id}/withdraw")
    ResponseEntity<Double> withdraw(@PathVariable Long id, @RequestParam Double amount) {
        log.info("Withdrawing {} from bank account with id: {}", amount, id);
        try {
            double deposit = bankAccountService.withdraw(amount, id);
            return ResponseEntity.status(HttpStatus.OK).body(deposit);
        } catch (BankAccountNotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException exception) {
            log.error("Withdraw failed. {}", exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping("/{id}/update-owner-name")
    ResponseEntity<BankAccount> updateOwnerName(@PathVariable Long id, @RequestParam String ownerName) {
        log.info("Updating owner name for bank account with id: {}", id);
        try {
            BankAccount bankAccount = bankAccountService.updateOwnerName(ownerName, id);
            return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
        } catch (BankAccountNotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}/delete")
    ResponseEntity<BankAccount> deleteBankAccountById(@PathVariable Long id) {
        log.info("Deleting bank account with id: {}", id);
        try {
            BankAccount bankAccount = bankAccountService.deleteBankAccountById(id);
            return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
        } catch (BankAccountNotFoundException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (BankAccountBalancePositiveException exception) {
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
