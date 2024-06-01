package com.elpidoroun.MarlowBank.model;

import com.elpidoroun.MarlowBank.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    private Account(){}
    private Account(Long id, BigDecimal balance, List<Transaction> transactions){
        this.id = id;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Account makeDeposit(BigDecimal depositAmount){
        var newBalance = this.balance.add(depositAmount);
        return new Account(this.id,
                newBalance,
                copyAndAddNewTransaction(Transaction.createDeposit(depositAmount, this, newBalance)));
    }
    public Account makeWithdrawal(BigDecimal withdrawalAmount){
        var newBalance = this.balance.subtract(withdrawalAmount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new ValidationException("Cannot withdraw: " + withdrawalAmount + " Balance is: " + balance);
        }

        var transaction = Transaction.createWithdrawal(withdrawalAmount, this, newBalance);

        return new Account(this.id, newBalance, copyAndAddNewTransaction(transaction));
    }

    private List<Transaction> copyAndAddNewTransaction(Transaction transaction){
        var newTransactionList = new ArrayList<>(this.transactions);
        newTransactionList.add(transaction);

        return newTransactionList;
    }
}
