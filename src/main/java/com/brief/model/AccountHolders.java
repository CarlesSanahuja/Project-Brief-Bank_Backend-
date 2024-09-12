package com.brief.model;

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

    private LocalDate dateOfBirth;
    private String name;

    @OneToOne
    private Address primaryAddress;

    @OneToOne
    private Address mailingAddress;  // Opcional

    @OneToMany(mappedBy = "primaryOwner")
    private List<Account> accounts;  // Lista de cuentas donde es primaryOwner
}
