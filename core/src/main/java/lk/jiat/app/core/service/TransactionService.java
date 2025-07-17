package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Remote
public interface TransactionService {
    Transaction getTransaction(int id);
    Transaction getTransactionByTransactionId(int id);
    Transaction getTransactionByCustomer(Long id);
    List<Transaction> getTransactionsByAccountId(Long accountId, String email);
    List<Transaction> getDailyTransactionVolume(LocalDate date);
    void saveTransaction(Account sourceAccountNo, Account destinationAccountNo, String description, double amount);
}
