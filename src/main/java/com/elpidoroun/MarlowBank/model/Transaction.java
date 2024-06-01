package com.elpidoroun.MarlowBank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@EqualsAndHashCode
@Getter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "type", nullable = false, length = 20)
    private String type; //should be enum

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "balance_after_transaction", nullable = false)
    private BigDecimal balanceAfterTransaction;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    private Transaction(){}

    private Transaction(BigDecimal amount, String type, BigDecimal balanceAfterTransaction, Account account) {
        this.amount = amount;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.type = type;
        this.account = account;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public static Transaction createWithdrawal(BigDecimal withdrawalAmount, Account account, BigDecimal newBalance){
        return new Transaction(withdrawalAmount, "withdrawal", newBalance, account); //type should be enum
    }

    public static Transaction createDeposit(BigDecimal depositAmount, Account account, BigDecimal newBalance){
        return new Transaction(depositAmount, "deposit", newBalance, account); //type should be enum
    }
}
