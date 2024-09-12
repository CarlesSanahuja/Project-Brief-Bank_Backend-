package com.brief.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class CreditCard extends Account{
    private BigDecimal creditLimit = BigDecimal.valueOf(100);  // Valor por defecto
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);  // Valor por defecto

    // Se permite ajustar el límite de crédito con restricciones
    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.valueOf(100000)) <= 0) {
            this.creditLimit = creditLimit;
        }
    }

    // Se permite ajustar el interés con restricciones
    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.valueOf(0.1)) >= 0) {
            this.interestRate = interestRate;
        }
    }
}
