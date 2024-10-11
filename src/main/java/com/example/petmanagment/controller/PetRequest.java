package com.example.petmanagment.controller;

//登録時に使用するリクエストパラメータ

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.petmanagment.constants.ValidationMessage.ANIMAL_SPECIES_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.BIRTHDAY_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.BIRTHDAY_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.NAME_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.NAME_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_POSITIVE_MESSAGE;

@Getter
@Setter
@NoArgsConstructor
public class PetRequest {

    @NotBlank(message = ANIMAL_SPECIES_ERROR_MESSAGE)
    @Pattern(regexp = "^(dog|cat)$", message = ANIMAL_SPECIES_ERROR_MESSAGE)
    private String animalSpecies;

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Size(max = 20, message = NAME_ERROR_MESSAGE)
    private String name;

    @NotNull(message = BIRTHDAY_NOT_BLANK_MESSAGE)
    @PastOrPresent(message = BIRTHDAY_ERROR_MESSAGE)
    private LocalDate birthday;

    @NotNull(message = WEIGHT_NOT_BLANK_MESSAGE)
    @Positive(message = WEIGHT_POSITIVE_MESSAGE)
    @Digits(integer = 3, fraction = 2, message = WEIGHT_ERROR_MESSAGE)
    private BigDecimal weight;

    public PetRequest(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }
}
