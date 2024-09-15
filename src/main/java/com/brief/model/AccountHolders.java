package com.brief.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AccountHolders extends User{
    private String name;

    private LocalDate dateOfBirth;

    @OneToOne
    private Address primaryAddress;

    @OneToOne
    private Address mailingAddress;  // Opcional

    @OneToMany(mappedBy = "primaryOwner")
    @JsonBackReference  // Esto indica la parte "trasera" de la relación
    private List<Account> accounts;  // Lista de cuentas donde es primaryOwner
}
