package com.example.petmanagment;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PetMapperTest {

    @Autowired
    private PetMapper petMapper;

    @Nested
    class READTests {

        @Test
        @DataSet(value = "datasets/pets.yml")
        @Transactional
        void 指定したIDの情報が取得できること() {
            Optional<Pet> actual = petMapper.findById(1);
            assertThat(actual).hasValue(new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("10.23")));
        }

        @Test
        @DataSet(value = "datasets/pets.yml")
        @Transactional
        void 指定したIDの登録が無い場合は空のOptionalが返されること() {
            Optional<Pet> actual = petMapper.findById(100);
            assertThat(actual).isEmpty();
        }
    }

    @Nested
    class CREATETest {

        @Test
        @DataSet(value = "datasets/pets.yml")
        @ExpectedDataSet(value = "datasets/expected_insert_pets.yml", ignoreCols = "id")
        @Transactional
        void 新規会員登録することが出来ること() {
            Pet pet = new Pet(null, "cat", "シロ", LocalDate.of(2011, 10, 9), new BigDecimal("3.94"));
            petMapper.insert(pet);
            Optional<Pet> insertPet = petMapper.findById(pet.getId());
            assertThat(insertPet).isPresent();
        }
    }

    @Nested
    class UPDATETest {

        @Test
        @DataSet(value = "datasets/pets.yml")
        @ExpectedDataSet(value = "datasets/expected_update_pets.yml")
        @Transactional
        void 指定したIDの体重を更新できること() {
            Pet pet = new Pet(1, "dog", "ポチ", LocalDate.of(2020, 1, 1), new BigDecimal("13.23"));
            petMapper.update(pet);

            Optional<Pet> updatePet = petMapper.findById(1);
            assertThat(updatePet).isPresent();
            assertThat(updatePet.get().getWeight()).isEqualTo(new BigDecimal("13.23"));
        }
    }

    @Nested
    class DELETETest {

        @Test
        @DataSet(value = "datasets/pets.yml")
        @ExpectedDataSet(value = "datasets/expected_delete_pets.yml")
        @Transactional
        void 指定したIDの情報を削除出来ること() {
            Pet pet = new Pet(4, "dog", "いちご", LocalDate.of(2024, 4, 24), new BigDecimal("4.94"));
            petMapper.delete(4);

            Optional<Pet> deletePet = petMapper.findById(4);
            assertThat(deletePet).isEmpty();
        }
    }

}