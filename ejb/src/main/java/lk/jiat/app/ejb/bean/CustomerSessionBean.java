package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.Status;
import lk.jiat.app.core.service.CustomerService;

import java.util.List;

@Stateless
public class CustomerSessionBean implements CustomerService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @Override
    public Customer findByEmail(String email) {
        try {
            return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Customer findByPhone(String phone) {
        try {
            return em.createNamedQuery("Customer.findByPhone", Customer.class).setParameter("mobile", phone).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String UpdateCustomerStatus(String email, Status status) {

        Customer customer = em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getSingleResult();
        customer.setStatus(status);

        em.merge(customer);

        return "Customer updated";

    }

    @Override
    public void registerCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        em.merge(customer);
    }
}
