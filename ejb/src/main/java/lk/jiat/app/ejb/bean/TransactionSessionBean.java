package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Transaction;
import lk.jiat.app.core.service.TransactionService;

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

}
