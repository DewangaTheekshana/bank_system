package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.Audit;

@Remote
public interface AuditService {
    void saveAudit(Audit audit);
}
