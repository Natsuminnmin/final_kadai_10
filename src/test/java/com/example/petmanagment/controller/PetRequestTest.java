package com.example.petmanagment.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.petmanagment.constants.ValidationMessage.ANIMAL_SPECIES_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.BIRTHDAY_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.BIRTHDAY_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.NAME_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.NAME_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_ERROR_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_NOT_BLANK_MESSAGE;
import static com.example.petmanagment.constants.ValidationMessage.WEIGHT_POSITIVE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PetRequestTest {

    @Autowired
    Validator validator;

    private PetRequest petRequest = new PetRequest();
    private BindingResult bindingResult = new BindException(petRequest, "PetRequest");

    //初めに値を設定しておく
    @BeforeEach
    public void before() {
        petRequest.setAnimalSpecies("dog");
        petRequest.setName("aaaaaaaaaaaaaaaaaaaa");
        petRequest.setBirthday(LocalDate.of(2021, 8, 31));
        petRequest.setWeight(new BigDecimal("3.45"));
    }

    @Test
    void 全てが入力されている時バリデーションエラーが起こらないこと() throws Exception {
        validator.validate(petRequest, bindingResult);
        assertThat(bindingResult.getFieldError()).isNull();
    }

    @Nested
    class AnimalSpeciesFieldTests {

        @Test
        void 動物種がNullの場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setAnimalSpecies(null);
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError animalSpeciesError = bindingResult.getFieldError("animalSpecies");
            assertThat(animalSpeciesError.getDefaultMessage()).isEqualTo(ANIMAL_SPECIES_ERROR_MESSAGE);
        }

        @Test
        void 動物種が空文字の場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setAnimalSpecies("");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError animalSpeciesError = bindingResult.getFieldError("animalSpecies");
            assertThat(animalSpeciesError.getDefaultMessage()).isEqualTo(ANIMAL_SPECIES_ERROR_MESSAGE);
        }

        @Test
        void 動物種が既定のもの以外が入力された場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setAnimalSpecies("犬");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError animalSpeciesError = bindingResult.getFieldError("animalSpecies");
            assertThat(animalSpeciesError.getDefaultMessage()).isEqualTo(ANIMAL_SPECIES_ERROR_MESSAGE);
        }
    }

    @Nested
    class NameFieldTests {
        @Test
        void 名前がNullの場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setName(null);
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError nameError = bindingResult.getFieldError("name");
            assertThat(nameError.getDefaultMessage()).isEqualTo(NAME_NOT_BLANK_MESSAGE);
        }

        @Test
        void 名前が空文字の場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setName("");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError nameError = bindingResult.getFieldError("name");
            assertThat(nameError.getDefaultMessage()).isEqualTo(NAME_NOT_BLANK_MESSAGE);
        }

        @Test
        void 名前が20字以上の場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setName("aaaaaaaaaaaaaaaaaaaaa");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError nameError = bindingResult.getFieldError("name");
            assertThat(nameError.getDefaultMessage()).isEqualTo(NAME_ERROR_MESSAGE);
        }
    }

    @Nested
    class BirthdayFieldTests {

        @Test
        void 誕生日がNullの場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setBirthday(null);
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError birthdayError = bindingResult.getFieldError("birthday");
            assertThat(birthdayError.getDefaultMessage()).isEqualTo(BIRTHDAY_NOT_BLANK_MESSAGE);
        }

        @Test
        void 誕生日が未来日だった場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setBirthday(LocalDate.of(2025, 5, 5));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError birthdayError = bindingResult.getFieldError("birthday");
            assertThat(birthdayError.getDefaultMessage()).isEqualTo(BIRTHDAY_ERROR_MESSAGE);
        }
    }

    @Nested
    class WeightFieldTests {

        @Test
        void 体重がNullの場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(null);
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError weightError = bindingResult.getFieldError("weight");
            assertThat(weightError.getDefaultMessage()).isEqualTo(WEIGHT_NOT_BLANK_MESSAGE);
        }

        @Test
        void 体重が負の数で入力されていた場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(new BigDecimal("-12.00"));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError weightError = bindingResult.getFieldError("weight");
            assertThat(weightError.getDefaultMessage()).isEqualTo(WEIGHT_POSITIVE_MESSAGE);
        }

        @Test
        void 体重が規定の数値内でない場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(new BigDecimal("1234.567"));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError weightError = bindingResult.getFieldError("weight");
            assertThat(weightError.getDefaultMessage()).isEqualTo(WEIGHT_ERROR_MESSAGE);
        }

        @Test
        void 体重が不正なフォーマットで入力されていた場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(new BigDecimal("-1234.567"));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            List<FieldError> weightErrors = bindingResult.getFieldErrors("weight");
            assertEquals(2, weightErrors.size());
            assertThat(weightErrors.get(0).getDefaultMessage()).isEqualTo(WEIGHT_POSITIVE_MESSAGE);
            assertThat(weightErrors.get(1).getDefaultMessage()).isEqualTo(WEIGHT_ERROR_MESSAGE);
        }
    }
}
