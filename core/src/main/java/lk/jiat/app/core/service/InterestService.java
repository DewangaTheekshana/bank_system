package lk.jiat.app.core.service;

import jakarta.ejb.Remote;
import lk.jiat.app.core.model.InterestAccrual;

import java.util.List;

@Remote
public interface InterestService {
    List<InterestAccrual> getInterestAccruals(Long accountId, String email);
}
