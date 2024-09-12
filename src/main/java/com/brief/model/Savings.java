package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Savings extends Account{
    private  String secretKey;
    private Long minimumBalance = 1000L;
    private Double interestRate = 0.0025;

    @Enumerated(EnumType.STRING)
    private Status status;
}
