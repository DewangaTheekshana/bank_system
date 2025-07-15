package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Transaction;
import lk.jiat.app.core.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TransactionSessionBean implements TransactionService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Transaction getTransaction(int id) {
        return em.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> getTransactionByCustomer(Long id) {
        return em.createNamedQuery("Transaction.findByCustomerId", Transaction.class)
                .setParameter("customerId", id).getResultList();

    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId, String email) {
        return em.createNamedQuery("Transaction.findByAccountId", Transaction.class).setParameter("accountId", accountId).setParameter("email", email).getResultList();
    }

    @Override
    public List<Transaction> getDailyTransactionVolume(LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59); // 23:59:59

        return em.createNamedQuery("Transaction.findByDailyTransactionVolume", Transaction.class).setParameter("start", startOfDay).setParameter("end", endOfDay).getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveTransaction(Account sourceAccountNo, Account destinationAccountNo, String description, double amount) {

        em.persist(new Transaction(sourceAccountNo, "TRANSFER", amount, description, LocalDateTime.now(), destinationAccountNo));

    }

}
