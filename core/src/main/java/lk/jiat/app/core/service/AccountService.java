package lk.jiat.app.core.service;

import lk.jiat.app.core.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountById(int id);
    Optional<Account> getAccountByAccountNumber(String accountNumber);
    List<Account> getAccountByUserEmail(String email);
    List<Account> getAccountsByAccountType(String accountType);
    List<Account> getAllAccounts();
    void addAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(int id);
}
