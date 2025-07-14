package lk.jiat.app.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.ScheduledStatusType;
import lk.jiat.app.core.model.Scheduled_Transfer;
import lk.jiat.app.core.service.TransferService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Singleton
@Startup
public class ScheduledTransferExecutorBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private TransferService transferService;

    @Schedule(hour = "*", minute = "*/1", persistent = false)
    public void processScheduledTransfers() {

        List<Scheduled_Transfer> activeTransaction = em.createNamedQuery("Scheduled.findByStatusAndTime", Scheduled_Transfer.class).setParameter("scheduleStatus", ScheduledStatusType.ACTIVE).setParameter("now", LocalDateTime.now()).getResultList();

        for (Scheduled_Transfer scheduled_transfer : activeTransaction) {

            try{
                transferService.transferAmount(
                        scheduled_transfer.getSourceAccount().getAccountNumber(),
                        scheduled_transfer.getTargetAccount().getAccountNumber(),
                        scheduled_transfer.getFrequency(),
                        scheduled_transfer.getAmount()
                );

                scheduled_transfer.setStatus(ScheduledStatusType.COMPLETE);
            }catch (Exception e) {
                scheduled_transfer.setStatus(ScheduledStatusType.FAILED);
            }

            em.merge(scheduled_transfer);

        }

    }

}
