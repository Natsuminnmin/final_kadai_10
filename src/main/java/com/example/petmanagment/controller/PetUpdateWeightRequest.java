package com.example.petmanagment.controller;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_POSITIVE_MESSAGE;

@Getter
@NoArgsConstructor
public class PetUpdateWeightRequest {

    @NotNull(message = WEIGHT_NOT_BLANK_MESSAGE)
    @Positive(message = WEIGHT_POSITIVE_MESSAGE)
    @Digits(integer = 3, fraction = 2, message = WEIGHT_ERROR_MESSAGE)
    private BigDecimal weight;

    public PetUpdateWeightRequest(BigDecimal weight) {
        this.weight = weight;
    }
}
