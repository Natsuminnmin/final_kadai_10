package com.example.petmanagment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PetMapper {

    @Select("SELECT * FROM pets WHERE id = #{id}")
    Optional<Pet> findById(int id);

    @Insert("INSERT INTO pets (animal_species, name, birthday, weight) VALUES (#{animalSpecies}, #{name}, #{birthday}, #{weight})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert (Pet pet);
}
