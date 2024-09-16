package com.brief.service;

import com.brief.model.*;
import com.brief.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createCheckingAccount(AccountHolders primaryOwner, AccountHolders secondaryOwner, BigDecimal balance) {
        int age = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();

        Account account;    // 4.1--
        if (age < 24) {
            account = new StudentChecking();
        } else {
            account = new Checking();
        }
        /*
        account.setPrimaryOwner(primaryOwner);
        account.setSecondaryOwner(secondaryOwner);
        account.setBalance(balance);
        account.applyPenaltyIfNecessary();
        return account; */

        // Configurar el balance, el owner, y otros datos comunes a las cuentas
        account.setPrimaryOwner(primaryOwner);
        account.setSecondaryOwner(secondaryOwner);
        account.setBalance(balance);
        account.applyPenaltyIfNecessary();  // ---------------
        account.setCreationDate(LocalDate.now()); // Establecer la fecha de creación

        // Guardar en la base de datos y devolver el objeto guardado
        return accountRepository.save(account);
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
        savingsAccount.setCreationDate(LocalDate.now()); // Establecer la fecha de creación

        // Guardar en la base de datos y devolver el objeto guardado
        return accountRepository.save(savingsAccount);
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
        creditCardAccount.setCreationDate(LocalDate.now()); // Establecer la fecha de creación

        // Guardar en la base de datos y devolver el objeto guardado
        return accountRepository.save(creditCardAccount);
    }

}
