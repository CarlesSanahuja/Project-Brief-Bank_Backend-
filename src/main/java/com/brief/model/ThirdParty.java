package com.brief.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ThirdParty extends User{
    private String hashedKey;
    private String name;
}
