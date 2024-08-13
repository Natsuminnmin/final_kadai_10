package com.example.petmanagment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pet {
    private Integer id;
    private String animalSpecies;
    private String name;
    private LocalDate birthday;
    private BigDecimal weight;

    public Pet(Integer id, String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.id = id;
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    //idは自動採番の為NULLにしている
    public Pet(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.id = null;
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public int getId() {
        return id;
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
