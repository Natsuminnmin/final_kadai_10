package com.example.petmanagment;

//登録時に使用するリクエストパラメータ

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PetPostRequest {

    @NotBlank
    @Size(max = 50)
    private String animalSpecies;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    @NotNull
    @Positive
    @Digits(integer = 3, fraction = 2)
    private Double weight;

}


