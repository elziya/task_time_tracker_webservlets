package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository {
    List<Account> findAll();
    Optional<Account> findByEmail(String email);
    void save(Account account);
}
