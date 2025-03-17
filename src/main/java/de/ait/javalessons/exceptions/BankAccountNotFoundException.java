package de.ait.javalessons.exceptions;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(Long bankAccountId) {
        super("Bank account with id: " + bankAccountId + " was not found");
    }
}
