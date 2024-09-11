package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Herencia para compartir propiedades comunes

public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long balance;

    private Long penaltyFee;

    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "primary_owner_id", nullable = false)
    private AccountHolders primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolders secondaryOwner;

    @Enumerated(EnumType.STRING)
    private Status status;

}
