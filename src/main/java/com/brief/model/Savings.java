package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Entity
public class Savings extends Account{
    private  String secretKey;
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);  // Valor por defecto
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000);  // Valor por defecto

    // Se permite ajustar el interés con restricciones
    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.valueOf(0.5)) <= 0) {
            this.interestRate = interestRate;
        }
    }

    // Se permite ajustar el saldo mínimo con restricciones
    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance.compareTo(BigDecimal.valueOf(100)) >= 0) {
            this.minimumBalance = minimumBalance;
        }
    }

    @Enumerated(EnumType.STRING)
    private Status status;
}
