package de.ait.configuration;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(BankAccountRepository bankAccountRepository) {
        return (args) -> {
            bankAccountRepository.save(new BankAccount("1001", "Alice Johnson", 1500.0));
            bankAccountRepository.save(new BankAccount("1002", "Bob Smith", 2500.0));
            bankAccountRepository.save(new BankAccount("1003", "Charlie Brown", 3500.0));
            bankAccountRepository.save(new BankAccount("1004", "David White", 4500.0));
            bankAccountRepository.save(new BankAccount("1005", "Emma Green", 5500.0));
            bankAccountRepository.save(new BankAccount("1006", "Frank Black", 6500.0));
            bankAccountRepository.save(new BankAccount("1007", "Grace Adams", 7500.0));
            bankAccountRepository.save(new BankAccount("1008", "Henry Scott", 8500.0));
            bankAccountRepository.save(new BankAccount("1009", "Isabella Lee", 9500.0));
            bankAccountRepository.save(new BankAccount("1010", "Jack Wilson", 10500.0));
        };
    }
}
