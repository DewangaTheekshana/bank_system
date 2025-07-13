package lk.jiat.app.core.service;

import lk.jiat.app.core.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountById(Long id);
    Account getAccountByAccountNumber(String accountNumber);
    Account getAccountByAccountIdAndEmailAndAccountType(Long accountId, String email, String accountType);
    List<Account> getAccountByUserEmail(String email);
    List<Account> getAccountsByAccountType(String accountType);
    List<Account> getAllAccounts();
    void creditToAccount(String accountNo, double amount);
    void debitFromAccount(String accountNo, double amount);
    void addAccount(Account account);
    void updateAccount(Account account);
    void deleteAccount(int id);
}
