package com.brief.service;

import com.brief.model.*;
import com.brief.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    // Método para obtener el balance de una cuenta por su ID
    public BigDecimal getAccountBalanceById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + id));
        return account.getBalance();
    }

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
    @Transactional
    public void transferMoney(Long sourceAccountId, Long destinationAccountId, String accountHolderName, BigDecimal amount) {

        // 1. Buscar las cuentas origen y destino
        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta de origen no fue encontrada"));

        Account destinationAccount = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new IllegalArgumentException("La cuenta de destino no fue encontrada"));

        // 2. Validar si la cuenta de origen tiene suficientes fondos
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Fondos insuficientes en la cuenta de origen");
        }

        // 3. Verificar si el nombre del titular coincide con la cuenta de destino
        AccountHolders primaryOwner = destinationAccount.getPrimaryOwner();
        AccountHolders secondaryOwner = destinationAccount.getSecondaryOwner();

        boolean isAccountHolderValid = (primaryOwner != null && primaryOwner.getName().equals(accountHolderName)) ||
                (secondaryOwner != null && secondaryOwner.getName().equals(accountHolderName));

        if (!isAccountHolderValid) {
            throw new IllegalArgumentException("El nombre del titular de la cuenta de destino no coincide");
        }

        // 4. Realizar la transferencia (restar del saldo de la cuenta de origen y sumar al saldo de la cuenta de destino)
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        // 5. Guardar las cuentas actualizadas
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }
}
