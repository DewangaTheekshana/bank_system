package lk.jiat.app.core.service;

import jakarta.ejb.Remote;

@Remote
public interface TransferService {
    void transferAmount(String sourceAccountNo, String destinationAccountNo, String description, double amount);
}
