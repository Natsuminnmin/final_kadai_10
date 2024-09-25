package com.example.petmanagment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetMapper petMapper;


    @Nested
    class READTests {
        @Test
        public void 指定したIDに紐づいている情報を取得できること() {
            doReturn(Optional.of(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23")))).when(petMapper).findById(1);
            Pet actual = petService.findPet(1);
            assertThat(actual).isEqualTo(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23")));
            verify(petMapper, times(1)).findById(1);
        }


        @Test
        public void 指定したIDの登録がない時にエラーメッセージが表示されること() {
            doReturn(Optional.empty()).when(petMapper).findById(100);
            assertThatThrownBy(() -> petService.findPet(100)).isInstanceOf(PetNotFoundException.class).hasMessage("入力されたIDの登録はありません。");
            verify(petMapper, times(1)).findById(100);
        }
    }

    @Nested
    class CREATETest {
        @Test
        public void 全項目が指定通りに入力されている場合は新規登録できること() {
            Pet pet = new Pet("cat", "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94"));
            Pet actual = petService.insert("cat", "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94"));
            assertThat(actual).isEqualTo(new Pet("cat", "ミケ", LocalDate.of(2019, 9, 9), new BigDecimal("6.94")));
            verify(petMapper, times(1)).insert(pet);
        }
    }

    @Nested
    class UPDATETests {

        @Test
        public void 指定したIDでIDに紐づいている情報の体重を更新することが出来ること() {
            Pet existingPet = new Pet(3, "cat", "リン", LocalDate.of(2017, 9, 10), new BigDecimal("8.94"));
            doReturn(Optional.of(existingPet)).when(petMapper).findById(3);
            Pet actual = petService.update(3, new BigDecimal("9.45"));
            assertThat(actual.getWeight()).isEqualTo(new BigDecimal("9.45"));
            assertThat(actual.getOldWeight()).isEqualTo(new BigDecimal("8.94"));
            verify(petMapper, times(1)).update(existingPet);
        }

        @Test
        public void 指定したIDの登録がない時に更新処理が出来ないこと() {
            doReturn(Optional.empty()).when(petMapper).findById(100);
            assertThrows(PetNotFoundException.class, () -> {
                petService.update(100, new BigDecimal("5.50"));
            });
            verify(petMapper, never()).delete(100);
        }

        @Test
        public void 指定したIDの登録がない時にエラーメッセージが表示されること() {
            doReturn(Optional.empty()).when(petMapper).findById(100);
            assertThatThrownBy(() -> petService.findPet(100)).isInstanceOf(PetNotFoundException.class).hasMessage("入力されたIDの登録はありません。");
            verify(petMapper, times(1)).findById(100);
        }
    }


    @Nested
    class DELETETest {
        @Test
        public void 指定したIDの情報を削除出来ること() {
            doReturn(Optional.of(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23")))).when(petMapper).findById(1);
            Pet actual = petService.delete(1);
            assertThat(actual).isEqualTo(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23")));
            verify(petMapper, times(1)).delete(1);
        }

        @Test
        public void 指定したIDの登録がない時に削除処理が出来ないこと() {
            doReturn(Optional.empty()).when(petMapper).findById(100);
            assertThrows(PetNotFoundException.class, () -> {
                petService.delete(100);
            });
            verify(petMapper, never()).delete(100);
        }

        @Test
        public void 指定したIDの登録がない時にエラーメッセージが表示されること() {
            doReturn(Optional.empty()).when(petMapper).findById(100);
            assertThatThrownBy(() -> petService.findPet(100)).isInstanceOf(PetNotFoundException.class).hasMessage("入力されたIDの登録はありません。");
            verify(petMapper, times(1)).findById(100);
        }
    }
}

