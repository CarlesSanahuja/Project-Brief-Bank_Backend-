package com.brief.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class CreditCard extends Account{
    private Long creditLimit_;
    private Long interestRate;
    private Long penaltyFee;
}
