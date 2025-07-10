package lk.jiat.app.core.service;

import lk.jiat.app.core.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction getTransaction(int id);
    List<Transaction> getTransactionByCustomer(Long id);
    List<Transaction> getTransactionsByAccountId(Long accountId, String email);
}
