package lk.jiat.app.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Cacheable(false)
@NamedQueries({
        @NamedQuery(name = "Transaction.findByCustomerId", query = "select t from Transaction t where t.account.customer.id =:customerId OR t.relatedAccount.customer.id =:customerId"),
        @NamedQuery(name = "Transaction.findByAccountId", query = "SELECT t FROM Transaction t WHERE (t.account.id = :accountId AND t.account.customer.email = :email) OR (t.relatedAccount.id = :accountId AND t.relatedAccount.customer.email = :email) ORDER BY t.id"),
        @NamedQuery(name = "Transaction.findByDailyTransactionVolume", query = "select t from Transaction t where t.createdAt between :start and :end")
})
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String transactionType;
    private double amount;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "related_account_id")
    private Account relatedAccount;

    public Transaction() {}

    public Transaction(Account account, String transactionType, double amount, String description, LocalDateTime createdAt, Account relatedAccount) {
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.relatedAccount = relatedAccount;
    }

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getRelatedAccount() {
        return relatedAccount;
    }

    public void setRelatedAccount(Account relatedAccount) {
        this.relatedAccount = relatedAccount;
    }
}
