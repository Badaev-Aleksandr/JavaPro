package de.ait.javalessons.service;

import de.ait.javalessons.exceptions.BankAccountBalancePositiveException;
import de.ait.javalessons.exceptions.BankAccountNotFoundException;
import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Value("${bank.min-balance:0.0}")
    private double minBalance;

    @Value("${bank.max-withdraw}")
    private double maxWithdraw;

    @Value("${bank.max-transfer-money}")
    private double maxTransferMoney;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount findBankAccountById(Long id) {
        log.info("Find bank account by id: {}", id);
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Bank account not found by id: {}", id);
                    return new BankAccountNotFoundException(id);
                });
    }

    public BankAccount saveNewBankAccount(BankAccount bankAccount) {
        log.info("Saving new bank account: {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
        // return null; Для теста
    }

    @Transactional
    public double deposit(double amount, Long bankAccountId) {
        BankAccount bankAccount = findBankAccountById(bankAccountId);
        validateTransaction(bankAccount, amount, "deposit");
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        return saveBankAccount.getBalance();
    }

    @Transactional
    public double withdraw(double amount, Long bankAccountId) {
        BankAccount bankAccount = findBankAccountById(bankAccountId);
        validateTransaction(bankAccount, amount, "withdraw");

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        return saveBankAccount.getBalance();

    }

    @Transactional
    public double transferMoney(Long fromBankAccountId, Long toBankAccountId, double amount) {
        BankAccount bankAccountFrom = findBankAccountById(fromBankAccountId);
        BankAccount bankAccountTo = findBankAccountById(toBankAccountId);

        validateTransaction(bankAccountFrom, amount, "transferMoney");

        bankAccountFrom.setBalance(bankAccountFrom.getBalance() - amount);
        bankAccountTo.setBalance(bankAccountTo.getBalance() + amount);
        bankAccountRepository.save(bankAccountFrom);
        bankAccountRepository.save(bankAccountTo);

        return bankAccountFrom.getBalance();
    }

    public BankAccount updateOwnerName(String ownerName, Long bankAccountId) {
        BankAccount bankAccount = findBankAccountById(bankAccountId);
        bankAccount.setOwnerName(ownerName);
        return bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccountById(Long bankAccountId) {
        BankAccount bankAccount = findBankAccountById(bankAccountId);
        if (bankAccount.getBalance() > 0) {
            log.error("Cannot delete bank account with id {} because balance is positive: {}",
                    bankAccountId, bankAccount.getBalance());
            throw new BankAccountBalancePositiveException(bankAccountId);
        }

        bankAccountRepository.deleteById(bankAccountId);
        log.info("Successfully deleted bank account with id {}", bankAccountId);
    }

    private void validateTransaction(BankAccount bankAccount, double amount, String operation) {
        if (amount <= 0) {
            log.error("{} failed: Amount must be greater than zero", operation);
            throw new IllegalArgumentException(operation + " failed: Amount must be greater than zero");
        }
        if (("withdraw".equals(operation) && amount > bankAccount.getBalance()) ||
                ("transferMoney".equals(operation) && amount > bankAccount.getBalance())) {
            log.error("{} failed: The amount exceeds the available balance", operation);
            throw new IllegalArgumentException(operation + " failed: The amount exceeds the available balance");
        }
        if (("withdraw".equals(operation) && bankAccount.getBalance() - amount < minBalance)
                || ("transferMoney".equals(operation) && bankAccount.getBalance() - amount < minBalance)) {
            log.error("{} failed: The transaction would reduce balance below the allowed minimum of {}",
                    operation, minBalance);
            throw new IllegalArgumentException(operation + " failed: The transaction would reduce balance below " + minBalance);
        }
        if ("withdraw".equals(operation) && amount > maxWithdraw) {
            log.error("{} failed: Withdraw amount exceeds max limit of {}", operation, maxWithdraw);
            throw new IllegalArgumentException(operation + " failed: Withdraw amount exceeds max limit of " + maxWithdraw);
        }
        if ("transferMoney".equals(operation) && amount > maxTransferMoney) {
            log.error("{} failed: Transaction amount exceeds the transfer limit of {}", operation, maxTransferMoney);
            throw new IllegalArgumentException(operation + " failed: Transaction amount exceeds the transfer limit of " + maxTransferMoney);
        }
    }
}
