package com.example.petmanagment;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class PetController {

    private PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pets/{id}")
    public Pet findPet(@PathVariable("id")int id){
        return petService.findPet(id);
    }

    @PostMapping("/pets")
    public ResponseEntity<PetResponse> insert(@RequestBody @Validated PetPostRequest petPostRequest, UriComponentsBuilder uriBuilder){
        Pet pet = petService.insert(petPostRequest.getAnimalSpecies(), petPostRequest.getName(), petPostRequest.getBirthday(), petPostRequest.getWeight());
        URI location = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri();
        PetResponse body = new PetResponse(petPostRequest.getName() + "-chan's registration has been completed!");
        return ResponseEntity.created(location).body(body);
    }
}

