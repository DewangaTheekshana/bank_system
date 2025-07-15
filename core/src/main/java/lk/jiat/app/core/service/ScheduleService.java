package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.ScheduledStatusType;
import lk.jiat.app.core.model.Scheduled_Transfer;
import lk.jiat.app.core.model.Status;

import java.time.LocalDateTime;
import java.util.List;

@Remote
public interface ScheduleService {
    void saveSchedule(Account sourceAccount, Account destinationAccount, double amount, LocalDateTime scheduledDateTime, String loggedInUserEmail);

    List<Scheduled_Transfer> getActiveSchedules(ScheduledStatusType statusType);
}
