package com.brief.model;
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

    // Relación con AccountHolder
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")  // Columna que relaciona con el PrimaryOwner
    private AccountHolders primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner_id", nullable = true)  // Secundario es opcional
    private AccountHolders secondaryOwner;

}
