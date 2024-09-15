package com.brief.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
    // 4.1 ------------------
    @Override
    public void applyPenaltyIfNecessary() {
        // CreditCard doesn't have a minimum balance, but penaltyFee still applies if needed
        super.applyPenaltyIfNecessary(BigDecimal.ZERO);
    }
    // 4.2 -----------------
    //  Añadimos la lógica para aplicar intereses mensualmente.
    // Aplicar intereses si ha pasado más de 1 mes desde la última vez que se aplicaron
    @Override
    public void applyInterest() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(super.getLastInterestDate(), now);

        if (period.getMonths() >= 1) {
            BigDecimal interest = getBalance().multiply(interestRate).divide(BigDecimal.valueOf(12)); // Interés mensual
            setBalance(getBalance().add(interest));
            setLastInterestDate(now); // Actualizar la fecha de la última aplicación de intereses
        }
    }
}
