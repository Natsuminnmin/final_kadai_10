package com.example.petmanagment;

import lombok.Getter;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
public class Pet {
    private Integer id;
    private String animalSpecies;
    private String name;
    private LocalDate birthday;
    private BigDecimal weight;

    @Transient // データベースに保存しないフィールド
    private BigDecimal oldWeight;

    public Pet(Integer id, String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.id = id;
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public void setWeight(BigDecimal weight) { //PetServiceクラスに値をセットする為
        this.weight = weight;
    }

    public Pet(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.id = null;
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public void setOldWeight(BigDecimal oldWeight)
    {
        this.oldWeight = oldWeight;
    }
}
