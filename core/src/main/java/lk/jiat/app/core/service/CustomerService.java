package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.Status;

import java.util.List;

@Remote
public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer findByEmail(String email);
    Customer findByPhone(String phone);
    String UpdateCustomerStatus(String email, Status status);
    void registerCustomer(Customer customer);
}
