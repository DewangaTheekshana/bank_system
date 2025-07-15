package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.ScheduledStatusType;
import lk.jiat.app.core.model.Scheduled_Transfer;
import lk.jiat.app.core.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ScheduleSessionBean implements ScheduleService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveSchedule(Account sourceAccount, Account destinationAccount, double amount, LocalDateTime scheduledDateTime, String loggedInUserEmail) {

        em.persist(new Scheduled_Transfer(sourceAccount, destinationAccount, amount, "ONETIME", scheduledDateTime, ScheduledStatusType.ACTIVE, LocalDateTime.now()));

    }

    @Override
    public List<Scheduled_Transfer> getActiveSchedules(ScheduledStatusType statusType) {
        return em.createNamedQuery("Scheduled.findByActiveSchedule", Scheduled_Transfer.class).setParameter("status", statusType).getResultList();
    }
}
