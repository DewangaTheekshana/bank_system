package lk.jiat.app.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.*;
import lk.jiat.app.core.service.AccountService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@Startup
public class InterestAccrualBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AccountService accountService;

    @Schedule(dayOfMonth = "15", hour = "18", minute = "34", persistent = false)
    public void processInterest() {

        System.out.println("Processing interest accrued");

        List<Account> savingAcc = accountService.getAllAccounts();

        double annualRate = 2;
        double fixAnnualRate = 5;
        double monthlyRate = annualRate / 12.0;
        double fixMonthlyRate = fixAnnualRate / 12.0;

        for (Account acc : savingAcc) {

            if (acc.getStatus().equals(Status.ACTIVE)) {

                if (acc.getAccountType().equals(AccountType.SAVING.name())) {
                    double interest = Math.round(acc.getBalance() * (monthlyRate / 100) * 100.0) / 100.0;

                    if (interest > 0.0) {
                        acc.setBalance(acc.getBalance() + interest);

                        InterestAccrual ia = new InterestAccrual(
                                acc,
                                interest,
                                LocalDate.now()
                        );

                        em.persist(ia);

                    }

                } else {

                    double interest2 = Math.round(acc.getBalance() * (fixMonthlyRate / 100) * 100.0) / 100.0;

                    if (interest2 > 0.0) {
                        acc.setBalance(acc.getBalance() + interest2);

                        InterestAccrual ia = new InterestAccrual(
                                acc,
                                interest2,
                                LocalDate.now()
                        );

                        em.persist(ia);

                    }

                }

            }

        }

    }

}
