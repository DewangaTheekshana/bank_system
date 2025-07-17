package lk.jiat.app.ejb.bean;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.User;
import lk.jiat.app.core.model.UserType;
import lk.jiat.app.core.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class UserSessionBean implements UserService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        try{
            return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return em.createNamedQuery("User.findAllUsers", User.class).getResultList();
    }

    @RolesAllowed("ADMIN")
    @Override
    public void addUser(String email, String password, UserType userType) {

        Customer customer = em.createNamedQuery("Cutomer.findByEmail", Customer.class).setParameter("email", email).getSingleResult();

        User user = new User(
                customer,
                customer.getFirstName()+"_"+customer.getLastName(),
                password,
                userType,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        em.persist(user);

    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public boolean validate(String email, String password) {

        System.out.println("email: " + email);
        System.out.println("password: " + password);

        User user = null;

        try {
            user = em.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No user found with email: " + email);
            return false;
        }

        System.out.println(user);

        return user.getPasswordHash().equals(password);
    }
}
