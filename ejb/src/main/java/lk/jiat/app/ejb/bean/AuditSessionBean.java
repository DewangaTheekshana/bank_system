package lk.jiat.app.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.jiat.app.core.model.Audit;
import lk.jiat.app.core.service.AuditService;

@Stateless
public class AuditSessionBean implements AuditService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveAudit(Audit audit) {
        em.persist(audit);
    }
}
