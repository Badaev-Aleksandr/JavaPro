package de.ait.javalessons.service;

import de.ait.javalessons.exceptions.BankAccountBalancePositiveException;
import de.ait.javalessons.exceptions.BankAccountNotFoundException;
import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BankAccountServiceTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Value("${bank.min-balance}")
    double minBalance;

    @Value("${bank.max-withdraw}")
    double maxWithdraw;

    @Value("${bank.max-transfer-money}")
    double maxTransferMoney;

    Long falseId = 777L;
    BankAccount bankAccountTest;

    private BankAccount accountOne;
    private BankAccount accountTwo;
    private BankAccount accountThree;
    private BankAccount accountFour;
    private BankAccount accountFive;
    private BankAccount accountSix;
    private BankAccount accountSeven;
    private BankAccount accountEight;
    private BankAccount accountNine;
    private BankAccount accountTen;

    @BeforeEach
    public void setUp() {
        bankAccountTest = new BankAccount();
        accountOne = new BankAccount("1001", "Alice Johnson", 1000.0);
        accountTwo = new BankAccount("1002", "Bob Smith", 2500.0);
        accountThree = new BankAccount("1003", "Charlie Brown", 3500.0);
        accountFour = new BankAccount("1004", "David White", 4500.0);
        accountFive = new BankAccount("1005", "Emma Green", 5500.0);
        accountSix = new BankAccount("1006", "Frank Black", 6500.0);
        accountSeven = new BankAccount("1007", "Grace Adams", 7500.0);
        accountEight = new BankAccount("1008", "Henry Scott", 8500.0);
        accountNine = new BankAccount("1009", "Isabella Lee", 0.0);
        accountTen = new BankAccount("1010", "Jack Wilson", 10500.0);

        bankAccountRepository.deleteAll();
        bankAccountRepository.saveAll(
                List.of(
                        accountOne,
                        accountTwo,
                        accountThree,
                        accountFour,
                        accountFive,
                        accountSix,
                        accountSeven,
                        accountEight,
                        accountNine,
                        accountTen
                )
        );
    }

    @Test
    void testGetAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        assertNotNull(bankAccounts);
        assertEquals(10, bankAccounts.size());
        assertEquals(accountOne.getId(), bankAccounts.get(0).getId());
    }

    @Test
    void testGetBankAccountByIdPositive() {
        bankAccountTest = bankAccountService.findBankAccountById(accountFive.getId());
        assertNotNull(bankAccountTest);
        assertEquals(accountFive.getId(), bankAccountTest.getId());
        assertEquals(accountFive.getBalance(), bankAccountTest.getBalance());
        assertEquals(accountFive.getAccountNumber(), bankAccountTest.getAccountNumber());
        assertEquals(accountFive.getOwnerName(), bankAccountTest.getOwnerName());
    }

    @Test
    void testGetBankAccountByIdNegative() {
        BankAccountNotFoundException exception = assertThrows(BankAccountNotFoundException.class,
                () -> bankAccountService.findBankAccountById(falseId));
        assertEquals("Bank account with id: " + falseId + " was not found", exception.getMessage());
    }

    @Test
    void testSaveBankAccount() {
        bankAccountTest = new BankAccount("999", "Badaev Aleksandr", 30000.0);
        BankAccount saveAccount = bankAccountService.saveNewBankAccount(bankAccountTest);
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        assertEquals(11, bankAccounts.size());
        assertNotNull(saveAccount);
        assertEquals(bankAccountTest.getAccountNumber(), saveAccount.getAccountNumber());
        assertEquals(bankAccountTest.getOwnerName(), saveAccount.getOwnerName());
        assertEquals(bankAccountTest.getBalance(), saveAccount.getBalance());
    }

    @Test
    void testDepositPositive() {
        double balance = bankAccountService.deposit(2000.0, accountTen.getId());
        assertNotNull(balance);
        assertEquals(bankAccountRepository.findById(accountTen.getId()).get().getBalance(), balance);
    }

    @Test
    void testDepositNegativeAmountByZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bankAccountService.deposit(0.0, accountTen.getId()));
        assertEquals("deposit failed: Amount must be greater than zero", exception.getMessage());
    }

    @Test
    void testDepositNegativeAccountNotFound() {
        BankAccountNotFoundException exception = assertThrows(BankAccountNotFoundException.class,
                () -> bankAccountService.deposit(1000.0, falseId));
        assertEquals("Bank account with id: " + falseId + " was not found", exception.getMessage());
    }

    @Test
    void testWithdrawPositive() {
        double balance = bankAccountService.withdraw(1000.0, accountEight.getId());
        assertNotNull(balance);
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountEight.getId());
        assertNotNull(bankAccount);
        assertEquals(bankAccount.get().getBalance(), balance);
        assertEquals(bankAccount.get().getAccountNumber(), accountEight.getAccountNumber());
    }

    @Test
    void testWithdrawMoney_ShouldThrowException_WhenInsufficientBalance() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bankAccountService.withdraw(1100.0, accountOne.getId()));
        assertEquals("withdraw failed: The amount exceeds the available balance", exception.getMessage());
    }

    @Test
    void testWithdrawMoney_ShouldThrowException_WhenBalanceGoesBelowMinimum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    bankAccountService.withdraw(980.0, accountOne.getId());
                });
        assertEquals("withdraw failed: The transaction would reduce balance below " + minBalance, exception.getMessage());
    }

    @Test
    void testWithdraw_ShouldThrowException_WhenAmountExceedsMaxLimit() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bankAccountService.withdraw(2000.0, accountFive.getId()));
        assertEquals("withdraw failed: Withdraw amount exceeds max limit of " + maxWithdraw, exception.getMessage());
    }

    @Test
    void testTransferMoneyPositive() {
        double balance = bankAccountService.transferMoney(accountEight.getId(), accountOne.getId(), 4000);
        assertNotNull(balance);
        assertEquals(bankAccountRepository.findById(accountEight.getId()).get().getBalance(), balance);
    }

    @Test
    void testTransferMoney_ShouldThrowException_WhenInsufficientBalance() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bankAccountService.transferMoney(accountOne.getId(), accountTwo.getId(), 4000));
        assertEquals("transferMoney failed: The amount exceeds the available balance", exception.getMessage());
    }

    @Test
    void testTransferMoney_ShouldThrowException_WhenBalanceGoesBelowMinimum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    bankAccountService.transferMoney(accountOne.getId(), accountTwo.getId(), 980.0);
                });
        assertEquals("transferMoney failed: The transaction would reduce balance below " + minBalance, exception.getMessage());
    }

    @Test
    void testTransferMoney_ShouldThrowException_WhenAmountExceedsMaxLimit() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    bankAccountService.transferMoney(accountTen.getId(), accountTwo.getId(), 5500.0);
                });
        assertEquals("transferMoney failed: Transaction amount exceeds the transfer limit of " + maxTransferMoney, exception.getMessage());
    }

    @Test
    void testBankAccountUpdateOwnerNamePositive() {
        bankAccountTest = bankAccountService.updateOwnerName("Badaev Aleksandr", accountOne.getId());
        assertNotNull(bankAccountTest);
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountOne.getId());
        assertEquals(bankAccount.get().getOwnerName(), bankAccountTest.getOwnerName());
        assertEquals(bankAccount.get().getBalance(), bankAccountTest.getBalance());
        assertEquals(bankAccount.get().getAccountNumber(), bankAccountTest.getAccountNumber());
        assertEquals(bankAccount.get().getId(), bankAccountTest.getId());
    }

    @Test
    void testDeleteBankAccountByIdPositive() {
        bankAccountService.deleteBankAccountById(accountNine.getId());
        assertEquals(9, bankAccountRepository.count());
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountNine.getId());
        assertTrue(bankAccount.isEmpty());
    }

    @Test
    void testDeleteBankAccountByIdNegative() {
        BankAccountBalancePositiveException exception = assertThrows(BankAccountBalancePositiveException.class,
                () -> bankAccountService.deleteBankAccountById(accountThree.getId()));
        assertEquals("Cannot delete bank account with id: " + accountThree.getId() + " because the balance is positive!",exception.getMessage());

    }
}
