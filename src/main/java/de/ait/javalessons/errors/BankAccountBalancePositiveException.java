package de.ait.javalessons.errors;

public class BankAccountBalancePositiveException extends RuntimeException {
    public BankAccountBalancePositiveException(Long bankAccountId) {
        super("Cannot delete bank account with id: " + bankAccountId + " because the balance is positive!");
    }
}
