package com.example.petmanagment;

//登録時に使用するリクエストパラメータ

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PetRequest {
    private String animalSpecies;
    private String name;
    private LocalDate birthday;
    private BigDecimal weight;
}


