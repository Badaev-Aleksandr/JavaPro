package de.ait.javalessons.repositories;

import de.ait.javalessons.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> id(Long id);
}
