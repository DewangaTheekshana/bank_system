package lk.jiat.app.core.service;

import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction getTransaction(int id);
    List<Transaction> getTransactionByCustomer(Long id);
    List<Transaction> getTransactionsByAccountId(Long accountId, String email);
    void saveTransaction(Account sourceAccountNo, Account destinationAccountNo, String description, double amount);
}
