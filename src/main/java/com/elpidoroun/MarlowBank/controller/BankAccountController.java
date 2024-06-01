package com.elpidoroun.MarlowBank.controller;

import com.elpidoroun.MarlowBank.model.Account;
import com.elpidoroun.MarlowBank.service.DepositService;
import com.elpidoroun.MarlowBank.service.WithdrawalService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/bank-account")
public class BankAccountController {

    @NonNull private final DepositService depositService;
    @NonNull private final WithdrawalService withdrawalService;

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Account> depositFunds(@PathVariable Long accountId, @RequestParam @Positive BigDecimal amount){
         return ResponseEntity.ok(depositService.execute(accountId, amount));
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Account> withdrawFunds(@PathVariable Long accountId, @RequestParam @Positive BigDecimal amount){
        return ResponseEntity.ok(withdrawalService.execute(accountId, amount));
    }
}
