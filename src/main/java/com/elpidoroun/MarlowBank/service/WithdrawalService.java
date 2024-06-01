package com.elpidoroun.MarlowBank.service;

import com.elpidoroun.MarlowBank.model.Account;
import com.elpidoroun.MarlowBank.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class WithdrawalService {

    @NonNull private final AccountRepository accountRepository;
    @NonNull private final LowBalanceAlertEventProducer lowBalanceAlertEventProducer;
    private final static BigDecimal alertLimit = new BigDecimal(100L);

    public synchronized Account execute(Long accountId, BigDecimal amount) {
        return accountRepository.findById(accountId)
                .map(original -> {
                    var updatedAccount = original.makeWithdrawal(amount);
                    boolean balanceFellBelow100 = balanceFellBelow100(original, updatedAccount);

                    accountRepository.save(updatedAccount);

                    if(balanceFellBelow100){
                        lowBalanceAlertEventProducer.sendEvent("Balance fell below 100 for account with ID: " + accountId + ". Balance is: " + updatedAccount.getBalance());
                    }
                    return updatedAccount;
                })
                .orElseThrow(() -> new EntityNotFoundException("Account with ID: " + accountId + " not found"));
    }

    public boolean balanceFellBelow100(Account original, Account updated){
        return original.getBalance().compareTo(alertLimit) >= 0
                && updated.getBalance().compareTo(alertLimit) < 0;
    }

}
