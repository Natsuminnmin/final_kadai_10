package com.example.petmanagment.controller;

//登録時に使用するリクエストパラメータ

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PetRequest {

    @NotBlank(message = "動物種を入力してください。")
    @Size(max = 20, message = "20文字以内で記入してください。")
    private String animalSpecies;

    @NotBlank(message = "名前を入力してください。")
    @Size(max = 20, message = "20文字以内で記入してください。")
    private String name;

    @NotNull(message = "誕生日を入力していださい。")
    @PastOrPresent(message = "有効な日付の範囲外です、誕生日を入力してください。")
    private LocalDate birthday;

    @NotNull(message = "体重を入力していださい。")
    @Positive(message = "正の数値で入力してださい。")
    @Digits(integer = 3, fraction = 2, message = "有効な数値の範囲外です。整数3桁、小数点以下2桁以内の範囲で入力して下さい。")
    private BigDecimal weight;

    public PetRequest(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        this.animalSpecies = animalSpecies;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }
}
