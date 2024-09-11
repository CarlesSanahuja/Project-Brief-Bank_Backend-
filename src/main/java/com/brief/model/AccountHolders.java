package com.brief.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AccountHolders extends User{
    private String name;
    private LocalDate dateOfBirth;
    private String primaryAddress;
    private String mailingAddress;
}
