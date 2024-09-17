package com.brief.controller;

import com.brief.model.*;
import com.brief.repository.AccountHoldersRepository;
import com.brief.repository.CheckingRepository;
import com.brief.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountHoldersRepository accountHoldersRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBalanceById() throws Exception {
        // Mockear el valor del balance de la cuenta
        Long accountId = 1L;
        BigDecimal balance = BigDecimal.valueOf(1000);
        when(accountService.getAccountBalanceById(accountId)).thenReturn(balance);

        // Realizar la solicitud GET y verificar que el contenido sea 1000
        mockMvc.perform(get("/accounts/{id}/balance", accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("1000"));  // Verificar directamente el valor del balance en la respuesta
    }

    @Test
    void createSavingsAccount() throws Exception {
        Long primaryOwnerId = 1L;

        // Crear un objeto de propietario simulado
        AccountHolders owner = new AccountHolders();
        owner.setId(primaryOwnerId);
        owner.setName("Gerard");

        // Simular que el repositorio devuelve el propietario
        when(accountHoldersRepository.findById(primaryOwnerId)).thenReturn(Optional.of(owner));

        // Parámetros de la cuenta de ahorro
        BigDecimal balance = BigDecimal.valueOf(3000);
        BigDecimal interestRate = BigDecimal.valueOf(0.0025);
        BigDecimal minimumBalance = BigDecimal.valueOf(1000);

        // Crear una cuenta de ahorro simulada
        Savings newSavingsAccount = new Savings();
        newSavingsAccount.setId(11L);
        newSavingsAccount.setBalance(balance);
        newSavingsAccount.setInterestRate(interestRate);
        newSavingsAccount.setMinimumBalance(minimumBalance);

        // Mockear el servicio para crear la cuenta
        when(accountService.createSavingsAccount(any(), any(), any(), any(), any()))
                .thenReturn(newSavingsAccount);

        // Realizar la solicitud POST y verificar los resultados
        mockMvc.perform(post("/accounts/savings")
                        .param("primaryOwnerId", String.valueOf(primaryOwnerId))
                        .param("balance", balance.toString())
                        .param("interestRate", interestRate.toString())
                        .param("minimumBalance", minimumBalance.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newSavingsAccount.getId()))
                .andExpect(jsonPath("$.balance").value(balance))
                .andExpect(jsonPath("$.interestRate").value(interestRate.doubleValue()))
                .andExpect(jsonPath("$.minimumBalance").value(minimumBalance.doubleValue()));
    }

    @Test
    void createCreditCardAccount() throws Exception {
        Long primaryOwnerId = 1L;

        // Crear un objeto de propietario simulado
        AccountHolders owner = new AccountHolders();
        owner.setId(primaryOwnerId);
        owner.setName("Gerard");

        // Simular que el repositorio devuelve el propietario
        when(accountHoldersRepository.findById(primaryOwnerId)).thenReturn(Optional.of(owner));

        // Parámetros de la cuenta de crédito
        BigDecimal balance = BigDecimal.valueOf(2000);
        BigDecimal creditLimit = BigDecimal.valueOf(1000);
        BigDecimal interestRate = BigDecimal.valueOf(0.2);

        // Crear una cuenta de crédito simulada
        CreditCard newCreditCardAccount = new CreditCard();
        newCreditCardAccount.setId(10L);
        newCreditCardAccount.setBalance(balance);
        newCreditCardAccount.setCreditLimit(creditLimit);
        newCreditCardAccount.setInterestRate(interestRate);

        // Mockear el servicio para crear la cuenta de crédito
        when(accountService.createCreditCardAccount(any(), any(), any(), any(), any()))
                .thenReturn(newCreditCardAccount);

        // Realizar la solicitud POST y verificar los resultados
        mockMvc.perform(post("/accounts/credit-card")
                        .param("primaryOwnerId", String.valueOf(primaryOwnerId))
                        .param("balance", balance.toString())
                        .param("creditLimit", creditLimit.toString())
                        .param("interestRate", interestRate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newCreditCardAccount.getId()))
                .andExpect(jsonPath("$.balance").value(balance))
                .andExpect(jsonPath("$.creditLimit").value(creditLimit))
                .andExpect(jsonPath("$.interestRate").value(interestRate.doubleValue()));
    }

    @Test
    void transferMoney() throws Exception {
        Long sourceAccountId = 1L;
        Long destinationAccountId = 2L;
        String accountHolderName = "John Doe";
        BigDecimal amount = BigDecimal.valueOf(500);

        // Mockear el servicio de transferencia
        Mockito.doNothing().when(accountService).transferMoney(sourceAccountId, destinationAccountId, accountHolderName, amount);

        mockMvc.perform(put("/accounts/transfer")
                        .param("sourceAccountId", String.valueOf(sourceAccountId))
                        .param("destinationAccountId", String.valueOf(destinationAccountId))
                        .param("accountHolderName", accountHolderName)
                        .param("amount", amount.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Transferencia realizada con éxito"));
    }
}