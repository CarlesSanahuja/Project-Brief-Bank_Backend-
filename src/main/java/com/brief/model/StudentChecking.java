package com.brief.model;

import com.brief.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class StudentChecking extends Account{
    private  String secretKey;

    @Enumerated(EnumType.STRING)
    private Status status;
}
