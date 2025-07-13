package lk.jiat.app.ejb.bean;

import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.*;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.service.AccountService;
import lk.jiat.app.core.service.TransactionService;
import lk.jiat.app.core.service.TransferService;

@Stateless
public class TransferServiceBean implements TransferService {

    @EJB
    private AccountService accountService;

    @EJB
    private TransactionService transactionService;

    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void transferAmount(String sourceAccountNo, String destinationAccountNo, String description, double amount) {

        Account senderAccount = accountService.getAccountByAccountNumber(sourceAccountNo);
        Account reciverAccount = accountService.getAccountByAccountNumber(destinationAccountNo);

        try {
            transactionService.saveTransaction(senderAccount, reciverAccount, description, amount);
            accountService.debitFromAccount(sourceAccountNo, amount);
            accountService.creditToAccount(destinationAccountNo, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
