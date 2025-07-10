package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
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
    public Optional<Account> getAccountById(int id) {
        return Optional.ofNullable(em.find(Account.class, id));
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {

        try {
            TypedQuery<Account> query = em.createNamedQuery("Account.findByAccountNumber", Account.class).setParameter("accountNumber", accountNumber);

            return Optional.ofNullable(query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
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
    public void addAccount(Account account) {

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(int id) {

    }
}
