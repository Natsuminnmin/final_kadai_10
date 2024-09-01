package com.example.petmanagment;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PetUpdateWeightRequest {

    @NotNull
    @Positive
    @Digits(integer = 3, fraction = 2)
    private BigDecimal weight;

}
