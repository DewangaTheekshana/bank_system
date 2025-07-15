package lk.jiat.app.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.Account;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.Status;
import lk.jiat.app.core.service.CustomerService;
import lk.jiat.app.core.service.RegisterService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Stateless
public class RegisterSessionBean implements RegisterService {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private CustomerService customerService;

    @Override
    public void registerCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void registerAccount(String customerEmail, String accountNumber, String accountType, double balance, BigDecimal interestRate, Status status, LocalDateTime createdAt) {

        Customer byEmail = customerService.findByEmail(customerEmail);

        em.persist(new Account(byEmail, accountNumber, accountType, balance, interestRate, status, createdAt));

    }


}
