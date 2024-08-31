package com.example.petmanagment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PetService {

    private final PetMapper petMapper;


    public PetService(PetMapper petMapper) {

        this.petMapper = petMapper;
    }

    public Pet findPet(int id) {
        Optional<Pet> pet = petMapper.findById(id);
        Pet Pet = pet.orElseThrow(() -> new PetNotFoundException("入力されたIDの登録はありません。"));
        return Pet;
    }


    public Pet insert(String animalSpecies, String name, LocalDate birthday, BigDecimal weight) {
        Pet pet = new Pet(animalSpecies, name, birthday, weight);
        petMapper.insert(pet);
        return pet;
    }


    public Pet findById(int id) {
        return petMapper.findById(id)
                .orElseThrow(() -> new PetNotFoundException("入力されたIDの登録はありません。"));
    }

    public Pet update(int id, BigDecimal weight) {
        Pet existingPet = findById(id);
        BigDecimal oldWeight = existingPet.getWeight();
        existingPet.setWeight(weight);
        petMapper.update(existingPet);
        existingPet.setOldWeight(oldWeight); // 前の体重をセット
        return existingPet;
    }
}

