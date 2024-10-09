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

import static com.example.petmanagment.controller.exception.ValidationMessage.*;
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
            assertThat(animalSpeciesError.getDefaultMessage()).isEqualTo(animalSpeciesErrorMessage);
        }

        @Test
        void 動物種が空文字の場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setAnimalSpecies("");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError animalSpeciesError = bindingResult.getFieldError("animalSpecies");
            assertThat(animalSpeciesError.getDefaultMessage()).isEqualTo(animalSpeciesErrorMessage);
        }

        @Test
        void 動物種が既定のもの以外が入力された場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setAnimalSpecies("犬");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError animalSpeciesError = bindingResult.getFieldError("animalSpecies");
            assertThat(animalSpeciesError.getDefaultMessage()).isEqualTo(animalSpeciesErrorMessage);
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
            assertThat(nameError.getDefaultMessage()).isEqualTo(nameNonBlankMessage);
        }

        @Test
        void 名前が空文字の場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setName("");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError nameError = bindingResult.getFieldError("name");
            assertThat(nameError.getDefaultMessage()).isEqualTo(nameNonBlankMessage);
        }

        @Test
        void 名前が20字以上の場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setName("aaaaaaaaaaaaaaaaaaaaa");
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError nameError = bindingResult.getFieldError("name");
            assertThat(nameError.getDefaultMessage()).isEqualTo(nameErrorMessage);
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
            assertThat(birthdayError.getDefaultMessage()).isEqualTo(birthdayNonBlankMessage);
        }

        @Test
        void 誕生日が未来日だった場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setBirthday(LocalDate.of(2025, 5, 5));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError birthdayError = bindingResult.getFieldError("birthday");
            assertThat(birthdayError.getDefaultMessage()).isEqualTo(birthdayErrorMessage);
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
            assertThat(weightError.getDefaultMessage()).isEqualTo(weightNonBlank);
        }

        @Test
        void 体重が負の数で入力されていた場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(new BigDecimal("-12.00"));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError weightError = bindingResult.getFieldError("weight");
            assertThat(weightError.getDefaultMessage()).isEqualTo(weightPositiveMessage);
        }

        @Test
        void 体重が規定の数値内でない場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(new BigDecimal("1234.567"));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            FieldError weightError = bindingResult.getFieldError("weight");
            assertThat(weightError.getDefaultMessage()).isEqualTo(weightErrorMessage);
        }

        @Test
        void 体重が不正なフォーマットで入力されていた場合バリデーションエラーが発生すること() throws Exception {
            petRequest.setWeight(new BigDecimal("-1234.567"));
            validator.validate(petRequest, bindingResult);

            assertThat(bindingResult.hasErrors());
            List<FieldError> weightErrors = bindingResult.getFieldErrors("weight");
            assertEquals(2, weightErrors.size());
            assertThat(weightErrors.get(0).getDefaultMessage()).isEqualTo(weightPositiveMessage);
            assertThat(weightErrors.get(1).getDefaultMessage()).isEqualTo(weightErrorMessage);
        }
    }
}
