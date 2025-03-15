package de.ait.javalessons.errors;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(Long bankAccountId) {
        super("Bank account with id: " + bankAccountId + " was not found");
    }
}
