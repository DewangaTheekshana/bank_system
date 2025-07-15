package lk.jiat.app.ejb.bean;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.Customer;
import lk.jiat.app.core.model.User;
import lk.jiat.app.core.service.UserService;

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
        User user = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email).getSingleResult();
        System.out.println(user);
        return user;
    }

    @RolesAllowed("ADMIN")
    @Override
    public void addUser(User user) {

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
