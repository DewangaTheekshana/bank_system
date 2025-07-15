package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.InterestAccrual;
import lk.jiat.app.core.service.InterestService;

import java.util.List;

@Stateless
public class InterestSessionBean implements InterestService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<InterestAccrual> getInterestAccruals(Long accountId, String email) {

        return em.createNamedQuery("Interest.findByAccountId", InterestAccrual.class).setParameter("accountid", accountId).setParameter("email", email).getResultList();
    }
}
