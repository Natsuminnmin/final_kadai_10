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
    public ResponseEntity<PetResponse> insert(@RequestBody @Validated PetRequest petRequest, UriComponentsBuilder uriBuilder){
        Pet pet = petService.insert(petRequest.getAnimalSpecies(), petRequest.getName(), petRequest.getBirthday(), petRequest.getWeight());
        URI location = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri();
        PetResponse body = new PetResponse(petRequest.getName() + "-chan's registration has been completed!");
        return ResponseEntity.created(location).body(body);
    }

}

