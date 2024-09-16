package com.brief.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Herencia para compartir propiedades comunes

public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    private BigDecimal penaltyFee = BigDecimal.valueOf(40);  // Valor por defecto para penalizaciones

    private LocalDate creationDate = LocalDate.now();

    // 4.2 ---------------------
    // Nueva propiedad para registrar la última vez que se añadieron intereses
    private LocalDate lastInterestDate = LocalDate.now();
    // -------------------------

    // Relación con AccountHolder
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "primary_owner_id")  // Columna que relaciona con el PrimaryOwner
    private AccountHolders primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner_id", nullable = true)  // Secundario es opcional
    private AccountHolders secondaryOwner;

    // 4.1 -------------------
    // Metodo para aplicar penalización si el saldo es menor que el saldo mínimo
    public void applyPenaltyIfNecessary(BigDecimal minimumBalance) {
        if (balance.compareTo(minimumBalance) < 0) {
            balance = balance.subtract(penaltyFee);
        }
    } // ---------------

    public abstract void applyPenaltyIfNecessary();
    // 4.2 ------------------------
    // Metodo abstracto para actualizar los intereses
    public abstract void applyInterest();
    // -----------------------------
}
