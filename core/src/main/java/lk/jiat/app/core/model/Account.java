package lk.jiat.app.core.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Cacheable(false)
@NamedQueries({
        @NamedQuery(name = "Account.findByAccountNumber", query = "select a from Account a where a.accountNumber =:accountNumber"),
        @NamedQuery(name = "Account.findByAccountType", query = "select a from Account a where a.accountType =:accountType"),
        @NamedQuery(name = "Account.findByAccountIdAndEmailAndAccountType", query = "select a from Account a where a.id =:accountId AND a.customer.email =:email AND a.accountType =:accountType"),
        @NamedQuery(name = "Account.findByUserEmail", query = "select a from Account a where a.customer.email =:email"),
        @NamedQuery(name = "Account.findAll", query = "select a from Account a")
})
public class Account implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    private LocalDateTime createdAt;

    public Account() {}

    public Account(Customer customer, String accountNumber, String accountType, BigDecimal balance, BigDecimal interestRate, Status status, LocalDateTime createdAt) {
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.interestRate = interestRate;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}