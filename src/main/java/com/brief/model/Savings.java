package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
    // 4.1 ------------
    @Override
    public void applyPenaltyIfNecessary() {
        super.applyPenaltyIfNecessary(minimumBalance);
    } // --------------
    // 4.2 -------------------------
    // Aplicar intereses si ha pasado más de 1 año desde la última vez que se aplicaron
    @Override
    public void applyInterest() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(super.getLastInterestDate(), now);

        if (period.getYears() >= 1) {
            BigDecimal interest = getBalance().multiply(interestRate); // Interés = saldo * tasa de interés
            setBalance(getBalance().add(interest));
            setLastInterestDate(now); // Actualizar la fecha de la última aplicación de intereses
        }
    }  // ------------------------------
}
