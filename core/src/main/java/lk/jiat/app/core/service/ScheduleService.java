package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Remote
public interface ScheduleService {
    void saveSchedule(Account sourceAccount, Account destinationAccount, double amount, LocalDateTime scheduledDateTime, String loggedInUserEmail);

    List<Scheduled_Transfer> getActiveSchedules(ScheduledStatusType statusType);
    List<Scheduled_Transfer> getScheduleTransactionsByAccountId(Long accountId, String email);
    Scheduled_Transfer getScheduleTransactionById(Long transactionId);
    void updateSchedule(Scheduled_Transfer scheduledTransfer);
}
