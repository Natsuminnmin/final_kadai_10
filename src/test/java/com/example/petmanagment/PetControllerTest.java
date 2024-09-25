package com.example.petmanagment;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void 指定したIDに紐づいている情報を1件取得できること() throws Exception {
        doReturn(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23"))).when(petService).findPet(1);
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
        verify(petService, times(1)).findPet(1);
    }

    @Test
    void 指定したIDの登録が無い場合はエラーメッセージと404のエラーが返されること() throws Exception {
        doThrow(new PetNotFoundException("入力されたIDの登録はありません。")).when(petService).findPet(100);
        mockMvc.perform(get("/pets/{id}", 100))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {                   
                        "message" : "入力されたIDの登録はありません。"
                        }                                         
                        """
                ));
        verify(petService, times(1)).findPet(100);
    }

    @Test
    void 全項目が正しく入力されている場合は新規登録できること() throws Exception {
        doReturn(new Pet("cat", "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94")))
                .when(petService).insert("cat", "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94"));
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {
                                "animalSpecies": "cat",
                                "name" : "ミケ",
                                "birthday" :"2019-09-09",
                                "weight" : 6.94                     
                                }
                                """
                ))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {                   
                        "message" : "ミケちゃんの登録が完了しました"
                        }  
                        """
                ));
        verify(petService, times(1)).insert("cat", "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94"));
    }

    @Test
    void 項目の内の一つでも抜けがある場合は新規登録できないこと() throws Exception {
        PetRequest petRequest = new PetRequest(null, "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94"));
        String petRequestJson = objectMapper.writeValueAsString(petRequest);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(petRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                                {
                                    "status": "BAD_REQUEST",
                                    "message": "validation error",
                                    "errors": [
                                        {
                                            "field": "animalSpecies",
                                            "message": "空白は許可されていません、動物種を入力してください。"
                                        }
                                    ]
                                }
                                """
                ));
    }

    @Test
    void 全項目が正しく入力されていない場合は新規登録できないこと() throws Exception {
        PetRequest petRequest = new PetRequest(null, null, null, null);
        String petRequestJson = objectMapper.writeValueAsString(petRequest);
        mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(petRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                             {
                                 "status": "BAD_REQUEST",
                                 "message": "validation error",
                                 "errors": [
                                     {
                                         "field": "birthday",
                                         "message": "誕生日を入力していださい。"
                                     },
                                     {
                                         "field": "weight",
                                         "message": "体重を入力していださい。"
                                     },
                                     {
                                         "field": "animalSpecies",
                                         "message": "空白は許可されていません、動物種を入力してください。"
                                     },
                                     {
                                         "field": "name",
                                         "message": "空白は許可されていません、名前を入力してください。"
                                     }
                                 ]
                             }                         
                        """
                ));
    }


    @Test
    void 指定したIDに紐づいているペットの体重を更新することが出来ること() throws Exception {
        Pet updatedPet = new Pet(3, "cat", "リン", LocalDate.of(2017, 9, 10), new BigDecimal("9.45"));
        updatedPet.setOldWeight(new BigDecimal("8.94"));
        doReturn(updatedPet).when(petService).update(3, new BigDecimal("9.45"));
        mockMvc.perform(patch("/pets/{id}", 3).contentType(MediaType.APPLICATION_JSON).content(
                        """
                                {
                                "weight" : 9.45
                                }
                                """
                ))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "message" : "リンちゃんの体重が更新されました。前回と比べて0.51kgの変化があります。"
                        }
                        """
                ));
        verify(petService, times(1)).update(3, new BigDecimal("9.45"));
    }

    @Test
    void 体重の入力が正しく無い場合は更新することが出来ないこと() throws Exception {
        PetUpdateWeightRequest petUpdateWeightRequest = new PetUpdateWeightRequest(null);
        String petUpdateWeightRequestJson = objectMapper.writeValueAsString(petUpdateWeightRequest);
        mockMvc.perform(patch("/pets/1").contentType(MediaType.APPLICATION_JSON).content(petUpdateWeightRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        """
                                {
                                    "status": "BAD_REQUEST",
                                    "message": "validation error",
                                    "errors": [
                                        {
                                            "field": "weight",
                                            "message": "体重を入力していださい。"
                                        }
                                    ]
                                }
                                """
                ));
    }

    @Test
    void 指定したIDのペットを1件削除出来ること() throws Exception {
        doReturn(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23"))).when(petService).delete(1);
        mockMvc.perform(delete("/pets/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "message" : "ポチちゃんの登録を削除しました"
                        }
                        """
                ));
        verify(petService, times(1)).delete(1);
    }

    @Test
    void 削除したいIDの登録が無い場合はエラーメッセージと404のエラーが返されること() throws Exception {
        doThrow(new PetNotFoundException("入力されたIDの登録はありません。")).when(petService).delete(1);
        mockMvc.perform(delete("/pets/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().json(
                        """
                                {
                                message : "入力されたIDの登録はありません。"
                                }
                                """
                ));
        verify(petService, times(1)).delete(1);
    }
}


