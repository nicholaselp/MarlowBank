package com.elpidoroun.MarlowBank.service;

import com.elpidoroun.MarlowBank.model.Account;
import com.elpidoroun.MarlowBank.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class DepositService {

    @NonNull private final AccountRepository accountRepository;

    public synchronized Account execute(Long accountId, BigDecimal amount) {
        return accountRepository.findById(accountId)
                .map(account -> accountRepository.save(account.makeDeposit(amount)))
                .orElseThrow(() -> new EntityNotFoundException("Account with ID: " + accountId + " not found"));
    }
}
