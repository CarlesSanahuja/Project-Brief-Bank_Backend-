package com.brief.controller;

import com.brief.model.Account;
import com.brief.model.AccountHolders;
import com.brief.model.CreditCard;
import com.brief.model.Savings;
import com.brief.repository.AccountHoldersRepository;
import com.brief.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountHoldersRepository accountHoldersRepository;
    @Autowired
    private AccountService accountService;
    @PostMapping("/checking")
    public ResponseEntity<Account> createCheckingAccount(
            @RequestParam Long primaryOwnerId,
            @RequestParam(required = false) Long secondaryOwnerId,
            @RequestParam BigDecimal balance) {

        // Buscar el dueño principal
        AccountHolders primaryOwner = accountHoldersRepository.findById(primaryOwnerId)
                .orElseThrow(() -> new IllegalArgumentException("Primary owner not found"));

        // Buscar el dueño secundario (si se ha proporcionado)
        AccountHolders secondaryOwner = null;
        if (secondaryOwnerId != null) {
            secondaryOwner = accountHoldersRepository.findById(secondaryOwnerId)
                    .orElseThrow(() -> new IllegalArgumentException("Secondary owner not found"));
        }

        // Crear la cuenta corriente
        Account checkingAccount = accountService.createCheckingAccount(primaryOwner, secondaryOwner, balance);
        return ResponseEntity.ok(checkingAccount);

    }

    // Crear Savings Account
    @PostMapping("/savings")
    public ResponseEntity<Savings> createSavingsAccount(
            @RequestParam Long primaryOwnerId,
            @RequestParam(required = false) Long secondaryOwnerId,
            @RequestParam BigDecimal balance,
            @RequestParam(required = false) BigDecimal interestRate,
            @RequestParam(required = false) BigDecimal minimumBalance) {

        AccountHolders primaryOwner = new AccountHolders(); // Debes obtener el objeto de la BD
        AccountHolders secondaryOwner = secondaryOwnerId != null ? new AccountHolders() : null;

        // Valores por defecto si no se especifican
        BigDecimal defaultInterestRate = interestRate != null ? interestRate : BigDecimal.valueOf(0.0025);
        BigDecimal defaultMinimumBalance = minimumBalance != null ? minimumBalance : BigDecimal.valueOf(1000);

        Savings savingsAccount = accountService.createSavingsAccount(primaryOwner, secondaryOwner, balance, defaultInterestRate, defaultMinimumBalance);
        return ResponseEntity.ok(savingsAccount);
    }

    // Crear CreditCard Account
    @PostMapping("/credit-card")
    public ResponseEntity<CreditCard> createCreditCardAccount(
            @RequestParam Long primaryOwnerId,
            @RequestParam(required = false) Long secondaryOwnerId,
            @RequestParam BigDecimal balance,
            @RequestParam(required = false) BigDecimal creditLimit,
            @RequestParam(required = false) BigDecimal interestRate) {

        AccountHolders primaryOwner = new AccountHolders(); // Debes obtener el objeto de la BD
        AccountHolders secondaryOwner = secondaryOwnerId != null ? new AccountHolders() : null;

        // Valores por defecto si no se especifican
        BigDecimal defaultCreditLimit = creditLimit != null ? creditLimit : BigDecimal.valueOf(100);
        BigDecimal defaultInterestRate = interestRate != null ? interestRate : BigDecimal.valueOf(0.2);

        CreditCard creditCardAccount = accountService.createCreditCardAccount(primaryOwner, secondaryOwner, balance, defaultCreditLimit, defaultInterestRate);
        return ResponseEntity.ok(creditCardAccount);
    }
}
