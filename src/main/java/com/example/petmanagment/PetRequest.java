package com.example.petmanagment;

import java.math.BigDecimal;
import java.time.LocalDate;

//登録時に使用するリクエストパラメータ
public class PetRequest {
    private String animalSpecies;
    private String name;
    private LocalDate birthday;
    private BigDecimal weight;

    public PetRequest(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public String getAnimalSpecies() {
        return animalSpecies;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
