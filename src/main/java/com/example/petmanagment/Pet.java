package com.example.petmanagment;

import lombok.Getter;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


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

    public void setOldWeight(BigDecimal oldWeight) {
        this.oldWeight = oldWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return Objects.equals(getId(), pet.getId()) && Objects.equals(getAnimalSpecies(), pet.getAnimalSpecies()) && Objects.equals(getName(), pet.getName()) && Objects.equals(getBirthday(), pet.getBirthday()) && Objects.equals(getWeight(), pet.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAnimalSpecies(), getName(), getBirthday(), getWeight());
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", animalSpecies='" + animalSpecies + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", oldWeight=" + oldWeight +
                '}';
    }
}


