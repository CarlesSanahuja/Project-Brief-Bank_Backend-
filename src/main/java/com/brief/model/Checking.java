package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Checking extends Account{

    private  String secretKey;
    private Long minimumBalance;
    private Long penaltyFee;
    private Long monthyMaintenanceFee;
    private LocalDate creationDate;

   @Enumerated(EnumType.STRING)
   private Status status;
}
