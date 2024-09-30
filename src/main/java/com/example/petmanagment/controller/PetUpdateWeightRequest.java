package com.example.petmanagment.controller;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PetUpdateWeightRequest {

    @NotNull(message = "体重を入力していださい。")
    @Positive(message = "正の数値で入力してださい。")
    @Digits(integer = 3, fraction = 2, message = "有効な数値の範囲外です。整数3桁、小数点以下2桁以内の範囲で入力して下さい。")
    private BigDecimal weight;

    public PetUpdateWeightRequest(BigDecimal weight) {
        this.weight = weight;
    }
}
