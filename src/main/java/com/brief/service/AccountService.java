package com.brief.service;

import com.brief.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
@Service
public class AccountService {
    public Account createCheckingAccount(AccountHolders primaryOwner, AccountHolders secondaryOwner, BigDecimal balance) {
        int age = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
        Account account;
        if (age < 24) { // 4.1---
            account = new StudentChecking();
        } else {
            account = new Checking();
        }
        account.setPrimaryOwner(primaryOwner);
        account.setSecondaryOwner(secondaryOwner);
        account.setBalance(balance);
        account.applyPenaltyIfNecessary();
        return account;
    }
 // ---
    public Savings createSavingsAccount(AccountHolders primaryOwner, AccountHolders secondaryOwner, BigDecimal balance, BigDecimal interestRate, BigDecimal minimumBalance) {
        Savings savingsAccount = new Savings();
        savingsAccount.setPrimaryOwner(primaryOwner);
        savingsAccount.setSecondaryOwner(secondaryOwner);
        savingsAccount.setBalance(balance);
        savingsAccount.setInterestRate(interestRate);
        savingsAccount.setMinimumBalance(minimumBalance);
        savingsAccount.applyPenaltyIfNecessary(); // ---
        savingsAccount.applyInterest();  // 4.2  Aplicar intereses si corresponde
        return savingsAccount;
    }

    public CreditCard createCreditCardAccount(AccountHolders primaryOwner, AccountHolders secondaryOwner, BigDecimal balance, BigDecimal creditLimit, BigDecimal interestRate) {
        CreditCard creditCardAccount = new CreditCard();
        creditCardAccount.setPrimaryOwner(primaryOwner);
        creditCardAccount.setSecondaryOwner(secondaryOwner);
        creditCardAccount.setBalance(balance);
        creditCardAccount.setCreditLimit(creditLimit);
        creditCardAccount.setInterestRate(interestRate);
        creditCardAccount.applyPenaltyIfNecessary(); // ----
        creditCardAccount.applyInterest();  // 4.2  Aplicar intereses si corresponde
        return creditCardAccount;
    }

}
