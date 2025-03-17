package de.ait.javalessons.exceptions;

public class BankAccountBalancePositiveException extends RuntimeException {
    public BankAccountBalancePositiveException(Long bankAccountId) {
        super("Cannot delete bank account with id: " + bankAccountId + " because the balance is positive!");
    }
}
