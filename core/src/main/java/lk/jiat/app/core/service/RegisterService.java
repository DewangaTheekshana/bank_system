package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Remote
public interface RegisterService {
    void registerCustomer(Customer customer);
    void registerAccount(String customerId, String accountNumber, String accountType, double balance, BigDecimal interestRate, Status status, LocalDateTime createdAt);
}
