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
public class StudentChecking extends Account{
    private  String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status;

    // 4.2 ---------------------
    // Sobrescribir el metodo abstracto 'applyInterest' para StudentChecking
    @Override
    public void applyInterest() {
        // StudentChecking no tiene intereses que aplicar, por lo que esta implementación está vacía
    } // -----------------------
    // 4.1 -------
    @Override
    public void applyPenaltyIfNecessary() {
        super.applyPenaltyIfNecessary(BigDecimal.ZERO);
    }  // ------
}
