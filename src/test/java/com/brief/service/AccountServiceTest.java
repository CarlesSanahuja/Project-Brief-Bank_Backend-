package com.brief.service;

import com.brief.model.*;
import com.brief.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAccountBalanceById() {
        // Preparar datos
        Checking account = new Checking();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));

        // Definir el comportamiento del mock
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        // Ejecutar el método
        BigDecimal balance = accountService.getAccountBalanceById(1L);

        // Validar el resultado
        assertEquals(BigDecimal.valueOf(1000), balance);
    }
    @Test
    void testCreateCheckingAccountForOlderOwner() {
        // Preparar datos
        AccountHolders primaryOwner = new AccountHolders();
        primaryOwner.setDateOfBirth(LocalDate.of(1990, 1, 1));

        AccountHolders secondaryOwner = new AccountHolders();

        // Crear el objeto Checking con el balance esperado
        Checking account = new Checking();
        account.setBalance(BigDecimal.valueOf(500)); // Aquí seteamos el balance

        // Definir el comportamiento del mock
        when(accountRepository.save(any(Checking.class))).thenReturn(account);

        // Ejecutar el método
        Account result = accountService.createCheckingAccount(primaryOwner, secondaryOwner, BigDecimal.valueOf(500));

        // Validar el resultado
        assertTrue(result instanceof Checking);
        assertEquals(BigDecimal.valueOf(500), result.getBalance());
    }

    @Test
    void testCreateCheckingAccountForYoungOwner() {
        // Preparar datos
        AccountHolders primaryOwner = new AccountHolders();
        primaryOwner.setDateOfBirth(LocalDate.of(2005, 1, 1));  // Dueño joven

        AccountHolders secondaryOwner = new AccountHolders();

        // Crear el objeto StudentChecking con el balance esperado
        StudentChecking account = new StudentChecking();
        account.setBalance(BigDecimal.valueOf(500)); // Configurar el balance del objeto mock

        // Definir el comportamiento del mock
        when(accountRepository.save(any(StudentChecking.class))).thenReturn(account);

        // Ejecutar el método
        Account result = accountService.createCheckingAccount(primaryOwner, secondaryOwner, BigDecimal.valueOf(500));

        // Validar el resultado
        assertTrue(result instanceof StudentChecking);  // Comprobar que es una instancia de StudentChecking
        assertEquals(BigDecimal.valueOf(500), result.getBalance());  // Comprobar el balance
    }
    @Test
    void testCreateSavingsAccount() {
        // Preparar datos
        AccountHolders primaryOwner = new AccountHolders();
        AccountHolders secondaryOwner = new AccountHolders();

        // Crear el objeto Savings con balance, interestRate y minimumBalance
        Savings savingsAccount = new Savings();
        savingsAccount.setBalance(BigDecimal.valueOf(1000));
        savingsAccount.setInterestRate(BigDecimal.valueOf(0.02));  // Configurar la tasa de interés
        savingsAccount.setMinimumBalance(BigDecimal.valueOf(500));  // Configurar el balance mínimo

        // Definir el comportamiento del mock
        when(accountRepository.save(any(Savings.class))).thenReturn(savingsAccount);

        // Ejecutar el método
        Savings result = accountService.createSavingsAccount(primaryOwner, secondaryOwner,
                BigDecimal.valueOf(1000), BigDecimal.valueOf(0.02), BigDecimal.valueOf(500));

        // Validar el resultado
        assertEquals(BigDecimal.valueOf(1000), result.getBalance());  // Verificar el balance
        assertEquals(BigDecimal.valueOf(0.02), result.getInterestRate());  // Verificar la tasa de interés
        assertEquals(BigDecimal.valueOf(500), result.getMinimumBalance());  // Verificar el balance mínimo
    }
    @Test
    void testCreateCreditCardAccount() {
        // Preparar datos
        AccountHolders primaryOwner = new AccountHolders();
        AccountHolders secondaryOwner = new AccountHolders();

        // Crear el objeto CreditCard con balance, creditLimit y interestRate
        CreditCard creditCardAccount = new CreditCard();
        creditCardAccount.setBalance(BigDecimal.valueOf(500));
        creditCardAccount.setCreditLimit(BigDecimal.valueOf(1000));  // Configurar el límite de crédito
        creditCardAccount.setInterestRate(BigDecimal.valueOf(0.15));  // Configurar la tasa de interés

        // Definir el comportamiento del mock
        when(accountRepository.save(any(CreditCard.class))).thenReturn(creditCardAccount);

        // Ejecutar el método
        CreditCard result = accountService.createCreditCardAccount(primaryOwner, secondaryOwner,
                BigDecimal.valueOf(500), BigDecimal.valueOf(1000), BigDecimal.valueOf(0.15));

        // Validar el resultado
        assertEquals(BigDecimal.valueOf(500), result.getBalance());  // Verificar el balance
        assertEquals(BigDecimal.valueOf(1000), result.getCreditLimit());  // Verificar el límite de crédito
        assertEquals(BigDecimal.valueOf(0.15), result.getInterestRate());  // Verificar la tasa de interés
    }

    @Test
    void testTransferMoney() {
        // Preparar datos
        Account sourceAccount = new Checking();
        sourceAccount.setId(1L);
        sourceAccount.setBalance(BigDecimal.valueOf(1000));

        Account destinationAccount = new Checking();
        destinationAccount.setId(2L);
        destinationAccount.setBalance(BigDecimal.valueOf(500));

        AccountHolders primaryOwner = new AccountHolders();
        primaryOwner.setName("John Doe");

        destinationAccount.setPrimaryOwner(primaryOwner);

        // Definir comportamiento del mock
        when(accountRepository.findById(1L)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(destinationAccount));

        // Ejecutar el método
        accountService.transferMoney(1L, 2L, "John Doe", BigDecimal.valueOf(300));

        // Validar que los balances se hayan actualizado
        assertEquals(BigDecimal.valueOf(700), sourceAccount.getBalance());
        assertEquals(BigDecimal.valueOf(800), destinationAccount.getBalance());
    }

}