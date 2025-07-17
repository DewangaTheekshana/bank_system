package lk.jiat.app.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_transfers")
@NamedQueries({
        @NamedQuery(name = "Scheduled.findByStatusAndTime", query = "SELECT s FROM Scheduled_Transfer s WHERE s.status = :scheduleStatus AND s.nextExecutionDate <= :now"),
        @NamedQuery(name = "Scheduled.findByActiveSchedule", query = "select s from Scheduled_Transfer s where s.status =:status"),
        @NamedQuery(name = "Scheduled.findByAccountIdAndEmail", query = "SELECT s FROM Scheduled_Transfer s WHERE s.sourceAccount.id = :accountId AND s.sourceAccount.customer.email = :email AND s.status =:status ORDER BY s.id")
})
public class Scheduled_Transfer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;

    private double amount;
    private String frequency;
    private LocalDateTime nextExecutionDate;

    @Enumerated(EnumType.STRING)
    private ScheduledStatusType status = ScheduledStatusType.ACTIVE;

    private LocalDateTime createdAt;

    public Scheduled_Transfer() {}

    public Scheduled_Transfer(Account sourceAccount, Account targetAccount, double amount, String frequency, LocalDateTime nextExecutionDate, ScheduledStatusType status, LocalDateTime createdAt) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.frequency = frequency;
        this.nextExecutionDate = nextExecutionDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getNextExecutionDate() {
        return nextExecutionDate;
    }

    public void setNextExecutionDate(LocalDateTime nextExecutionDate) {
        this.nextExecutionDate = nextExecutionDate;
    }

    public ScheduledStatusType getStatus() {
        return status;
    }

    public void setStatus(ScheduledStatusType status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}