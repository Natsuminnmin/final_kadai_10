package com.example.petmanagment.controller;

//登録時に使用するリクエストパラメータ

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.petmanagment.controller.exception.ValidationMessage.*;

@Getter
@Setter
@NoArgsConstructor
public class PetRequest {

    @NotBlank(message = animalSpeciesErrorMessage)
    @Pattern(regexp = "^(dog|cat)$", message = animalSpeciesErrorMessage)
    private String animalSpecies;

    @NotBlank(message = nameNonBlankMessage)
    @Size(max = 20, message = nameErrorMessage)
    private String name;

    @NotNull(message = birthdayNonBlankMessage)
    @PastOrPresent(message = birthdayErrorMessage)
    private LocalDate birthday;

    @NotNull(message = weightNonBlank)
    @Positive(message = weightPositiveMessage)
    @Digits(integer = 3, fraction = 2, message = weightErrorMessage)
    private BigDecimal weight;

    public PetRequest(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }
}
