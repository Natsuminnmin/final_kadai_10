package com.example.petmanagment;

import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface PetMapper {

    @Select("SELECT * FROM pets WHERE id = #{id}")
    Optional<Pet> findById(int id);

    @Insert("INSERT INTO pets (animal_species, name, birthday, weight) VALUES (#{animalSpecies}, #{name}, #{birthday}, #{weight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Pet pet);

    @Update("UPDATE pets SET weight = #{weight} WHERE id = #{id}")
    void update(Pet pet);
}
