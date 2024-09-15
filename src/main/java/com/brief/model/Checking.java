package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Checking extends Account{

    private BigDecimal minimumBalance = BigDecimal.valueOf(250);
    private BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);
    private String secretKey;

   @Enumerated(EnumType.STRING)
   private Status status;

    // 4.2 --------------------------
    // Implementación del metodo applyInterest(), pero vacío ya que Checking no genera intereses
    @Override
    public void applyInterest() {
        // Las cuentas Checking no aplican intereses, por lo que esta implementación está vacía
    } // ---------------------

    // 4.1 ---------------
    @Override
    public void applyPenaltyIfNecessary() {
        super.applyPenaltyIfNecessary(minimumBalance);
    } // -----
}
