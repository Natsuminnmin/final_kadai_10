package com.example.petmanagment.integrationtest;


import com.example.petmanagment.controller.PetRequest;
import com.example.petmanagment.controller.PetUpdateWeightRequest;
import com.example.petmanagment.entity.Pet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class READTests {
        @Test
        @DataSet(value = "datasets/pets.yml")
        @Transactional
        void 指定したIDに紐づいている情報を1件取得できること() throws Exception {
            mockMvc.perform(get("/pets/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                            "id" : 1,
                            "animalSpecies": "dog",
                            "name" : "ポチ",
                            "birthday" :"2020-01-01",
                            "weight" : 10.23                     
                            }
                            """
                    ));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 指定したIDの登録が無い場合はエラーメッセージと404のエラーが返されること() throws Exception {
            mockMvc.perform(get("/pets/{id}", 100))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Not Found"))
                    .andExpect(jsonPath("$.message").value("入力されたIDの登録はありません。"))
                    .andExpect(jsonPath("$.path").value("/pets/100"));
        }
    }

    @Nested
    class CREATETests {
        @Test
        @DataSet(value = "datasets/pets.yml")
        @ExpectedDataSet(value = "datasets/expected_insert_pets.yml", ignoreCols = "id")
        @Transactional
        void 全項目が正しく入力されている場合は新規登録できること() throws Exception {
            PetRequest petRequest = new PetRequest("cat", "シロ", LocalDate.of(2011, 10, 9), new BigDecimal("3.94"));
            String petRequestJson = objectMapper.writeValueAsString(petRequest);
            mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(petRequestJson))
                    .andExpect(status().isCreated())
                    .andExpect(content().json("""
                            {                   
                            "message" : "シロちゃんの登録が完了しました"
                            }  
                            """
                    ));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 項目の内の一つでも抜けがある場合は新規登録できないこと() throws Exception {
            PetRequest petRequest = new PetRequest(null, "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94"));
            String petRequestJson = objectMapper.writeValueAsString(petRequest);
            String animalSpeciesErrorMessage = ANIMAL_SPECIES_ERROR_MESSAGE;
            String errorResponse = String.format("""
                    {
                        "status": "BAD_REQUEST",
                        "message": "validation error",
                        "errors": [
                            {
                                "field": "animalSpecies",
                                "message": "%s"
                            }
                        ]
                    }
                    """, ANIMAL_SPECIES_ERROR_MESSAGE
            );
            mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(petRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(errorResponse));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 全項目が正しく入力されていない場合は新規登録できないこと() throws Exception {
            PetRequest petRequest = new PetRequest(null, null, null, null);
            String petRequestJson = objectMapper.writeValueAsString(petRequest);
            String animalSpeciesErrorMessage = ANIMAL_SPECIES_ERROR_MESSAGE;
            String nameNotBlankMessage = NAME_NOT_BLANK_MESSAGE;
            String birthdayNotBlankMessage = BIRTHDAY_NOT_BLANK_MESSAGE;
            String weightNotBlankMessage = WEIGHT_NOT_BLANK_MESSAGE;
            String errorResponse = String.format("""
                         {
                             "status": "BAD_REQUEST",
                             "message": "validation error",
                             "errors": [
                                 {
                                     "field": "animalSpecies",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "name",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "birthday",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "weight",
                                     "message": "%s"
                                 }
                             ]
                         }                         
                    """, ANIMAL_SPECIES_ERROR_MESSAGE, NAME_NOT_BLANK_MESSAGE, BIRTHDAY_NOT_BLANK_MESSAGE, WEIGHT_NOT_BLANK_MESSAGE);
            mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(petRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(errorResponse));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 不正なフォーマットで入力されている場合は新規会員登録出来ないこと() throws Exception {
            PetRequest petRequest = new PetRequest("犬", "aaaaaaaaaaaaaaaaaaaaa", LocalDate.of(2025, 10, 10), new BigDecimal("-1140.568"));
            String petRequestJson = objectMapper.writeValueAsString(petRequest);
            String animalErrorMessage = ANIMAL_SPECIES_ERROR_MESSAGE;
            String nameErrorMessage = NAME_ERROR_MESSAGE;
            String birthdayErrorMessage = BIRTHDAY_ERROR_MESSAGE;
            String weightErrorMessage = WEIGHT_ERROR_MESSAGE;
            String weightPositiveMessage = WEIGHT_POSITIVE_MESSAGE;
            String errorResponse = String.format("""
                            {
                            "status": "BAD_REQUEST",
                            "message": "validation error",
                             "errors": [
                                 {
                                     "field": "animalSpecies",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "name",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "birthday",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "weight",
                                     "message": "%s"
                                 },
                                 {
                                     "field": "weight",
                                     "message": "%s"
                                 }
                         ]   
                         
                         }                  
                                                                    
                    """, ANIMAL_SPECIES_ERROR_MESSAGE, NAME_ERROR_MESSAGE, BIRTHDAY_ERROR_MESSAGE, WEIGHT_POSITIVE_MESSAGE, WEIGHT_ERROR_MESSAGE);
            mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(petRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(errorResponse));
        }
    }

    @Nested
    class UPDATETests {
        @Test
        @DataSet(value = "datasets/pets.yml")
        @ExpectedDataSet(value = "datasets/expected_update_pets.yml")
        @Transactional
        void 指定したIDに紐づいているペットの体重を更新することが出来ること() throws Exception {
            Pet updatedPet = new Pet(1, "dog", "ポチ", LocalDate.of(2020, 01, 01), new BigDecimal("10.23"));
            updatedPet.setOldWeight(new BigDecimal("13.23"));
            mockMvc.perform(patch("/pets/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(
                            """
                                    {
                                    "weight" : 13.23
                                    }
                                    """
                    ))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                            "message" : "ポチちゃんの体重が更新されました。前回と比べて3.00kgの変化があります。"
                            }
                            """
                    ));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 体重の入力が正しく無い場合は更新することが出来ないこと() throws Exception {
            PetUpdateWeightRequest petUpdateWeightRequest = new PetUpdateWeightRequest(null);
            String petUpdateWeightRequestJson = objectMapper.writeValueAsString(petUpdateWeightRequest);
            String weightNotBlankMessage = WEIGHT_NOT_BLANK_MESSAGE;
            String errorResponse = String.format("""
                    {
                        "status": "BAD_REQUEST",
                        "message": "validation error",
                        "errors": [
                            {
                                "field": "weight",
                                "message": "%s"
                            }
                        ]
                    }
                    """, WEIGHT_NOT_BLANK_MESSAGE);
            mockMvc.perform(patch("/pets/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(petUpdateWeightRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(errorResponse));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 不正なフォーマットで体重が入力されている場合は更新することが出来ないこと() throws Exception {
            PetUpdateWeightRequest petUpdateWeightRequest = new PetUpdateWeightRequest(new BigDecimal("-1234.567"));
            String petUpdateWeightRequestJson = objectMapper.writeValueAsString(petUpdateWeightRequest);
            String weightPositiveMessage = WEIGHT_POSITIVE_MESSAGE;
            String weightErrorMessage = WEIGHT_ERROR_MESSAGE;
            String errorResponse = String.format("""
                    {
                        "status": "BAD_REQUEST",
                        "message": "validation error",
                        "errors": [
                            {
                                "field": "weight",
                                "message": "%s"
                            },
                            {
                                "field": "weight",
                                "message": "%s"
                            }
                        ]
                    }
                     """, WEIGHT_POSITIVE_MESSAGE, WEIGHT_ERROR_MESSAGE);
            mockMvc.perform(patch("/pets/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(petUpdateWeightRequestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json(errorResponse));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 指定したIDの登録が無い場合はエラーメッセージと404のエラーが返されること() throws Exception {
            PetUpdateWeightRequest petUpdateWeightRequest = new PetUpdateWeightRequest(new BigDecimal("3.56"));
            String petUpdateWeightRequestJson = objectMapper.writeValueAsString(petUpdateWeightRequest);
            mockMvc.perform(patch("/pets/{id}", 100).contentType(MediaType.APPLICATION_JSON).content(petUpdateWeightRequestJson))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Not Found"))
                    .andExpect(jsonPath("$.message").value("入力されたIDの登録はありません。"))
                    .andExpect(jsonPath("$.path").value("/pets/100"));
        }
    }

    @Nested
    class DELETETests {
        @Test
        @DataSet(value = "datasets/pets.yml")
        @ExpectedDataSet(value = "datasets/expected_delete_pets.yml")
        @Transactional
        void 指定したIDのペットを1件削除出来ること() throws Exception {
            mockMvc.perform(delete("/pets/{id}", 4))
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                            {
                            "message" : "いちごちゃんの登録を削除しました"
                            }
                            """
                    ));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        void 削除したいIDの登録が無い場合はエラーメッセージと404のエラーが返されること() throws Exception {
            mockMvc.perform(delete("/pets/{id}", 100))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.timestamp").exists())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Not Found"))
                    .andExpect(jsonPath("$.message").value("入力されたIDの登録はありません。"))
                    .andExpect(jsonPath("$.path").value("/pets/100"));
        }
    }
}
