package de.ait.javalessons.service;

import de.ait.javalessons.errors.BankAccountBalancePositiveException;
import de.ait.javalessons.errors.BankAccountNotFoundException;
import de.ait.javalessons.model.BankAccount;
import de.ait.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    @Value("${bank.min-balance:0.0}")
    private double minBalance;

    @Value("${bank.max-withdraw}")
    private double maxWithdraw;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount findBankAccountById(Long id) {
        log.info("Find bank account by id: {}", id);
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));
    }

    public BankAccount saveNewBankAccount(BankAccount bankAccount) {
        log.info("Saving new bank account: {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public double deposit(double amount, Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));
        if (amount <= 0) {
            log.error("Amount must be greater than zero");
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        return saveBankAccount.getBalance();
    }

    @Transactional
    public double withdraw(double amount, Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));
        if (amount <= 0) {
            log.error("Deposit amount is less than or equal to zero");
            throw new IllegalArgumentException("Deposit amount is less than or equal to zero");
        }
        if (amount > maxWithdraw) {
            log.error("Withdraw amount exceeds max withdraw limit");
            throw new IllegalArgumentException("Withdraw amount exceeds max withdraw limit " + maxWithdraw);
        }
        if (amount > bankAccount.getBalance()) {
            log.error("Withdraw amount is greater than bank account balance");
            throw new IllegalArgumentException("Withdraw amount is greater than bank account balance");
        }
        if (bankAccount.getBalance() - amount < minBalance) {
            log.error("The current balance is less than the minimum balance");
            throw new IllegalArgumentException("The current balance is less than the minimum balance " + minBalance);
        } else {
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
            return saveBankAccount.getBalance();
        }
    }

    public BankAccount updateOwnerName(String overName, Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));

        bankAccount.setOwnerName(overName);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount deleteBankAccountById(Long bankAccountId) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotFoundException(bankAccountId));
        if (bankAccount.getBalance() > 0){
            throw new BankAccountBalancePositiveException(bankAccountId);
        }

        bankAccountRepository.deleteById(bankAccountId);
        return bankAccount;
    }
}
