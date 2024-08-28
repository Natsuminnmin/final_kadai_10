package com.example.petmanagment;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Pet {
    private Integer id;
    private String animalSpecies;
    private String name;
    private LocalDate birthday;
    private Double weight;

    public Pet(Integer id, String animalSpecies, String name, LocalDate birthday, Double weight) {
        this.id = id;
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public Pet(String animalSpecies, String name, LocalDate birthday, Double weight) {
        this.id = null;
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }
}


