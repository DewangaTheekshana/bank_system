package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.service.AccountService;

import java.util.List;
import java.util.Optional;

@Stateless
public class AccountSessionBean implements AccountService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Account> getAccountById(Long id) {
        return Optional.ofNullable(em.find(Account.class, id));
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {

        try {
            return em.createNamedQuery("Account.findByAccountNumber", Account.class).setParameter("accountNumber", accountNumber).getSingleResult();

        }catch (NoResultException e){
            return null;
        }

    }

    @Override
    public Account getAccountByAccountIdAndEmailAndAccountType(Long accountId, String email, String accountType) {
        try {
            return em.createNamedQuery("Account.findByAccountIdAndEmailAndAccountType", Account.class).setParameter("accountId", accountId).setParameter("email", email).setParameter("accountType", accountType).getSingleResult();

        }catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Account> getAccountByUserEmail(String email) {

        TypedQuery<Account> query = em.createNamedQuery("Account.findByUserEmail", Account.class).setParameter("email", email);
        return query.getResultList();

    }

    @Override
    public List<Account> getAccountsByAccountType(String accountType) {
        TypedQuery<Account> query = em.createNamedQuery("Account.findByAccountType", Account.class)
                .setParameter("accountType", accountType);
        return query.getResultList();
    }

    @Override
    public List<Account> getAllAccounts() {
        return em.createNamedQuery("Account.findAll", Account.class).getResultList();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void creditToAccount(String accountNo, double amount) {

        try {

            Account account = em.createNamedQuery("Account.findByAccountNumber", Account.class).setParameter("accountNumber", accountNo).getSingleResult();

            if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
                System.out.println("credit to account: " + accountNo);
            }

            em.merge(account);

        }catch (NoResultException e){
            e.printStackTrace();
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void debitFromAccount(String accountNo, double amount) {

        try {

            Account account = em.createNamedQuery("Account.findByAccountNumber", Account.class).setParameter("accountNumber", accountNo).getSingleResult();

            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                System.out.println("debit to account: " + accountNo);

                em.merge(account);
            }

        }catch (NoResultException e){
            e.printStackTrace();
        }

    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(int id) {

    }
}
