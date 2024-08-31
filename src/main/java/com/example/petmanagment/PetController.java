package com.example.petmanagment;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
public class PetController {

    private PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pets/{id}")
    public Pet findPet(@PathVariable("id")int id) //idを使ってデータベースから情報を取得
    {
        return petService.findPet(id);
    }

    @PostMapping("/pets")
    public ResponseEntity<PetResponse> insert(@RequestBody @Validated PetRequest petRequest, UriComponentsBuilder uriBuilder){
        Pet pet = petService.insert(petRequest.getAnimalSpecies(), petRequest.getName(), petRequest.getBirthday(), petRequest.getWeight());
        URI location = uriBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri();
        PetResponse body = new PetResponse(petRequest.getName() + "ちゃんの登録が完了しました");
        return ResponseEntity.created(location).body(body);
    }

    @PatchMapping("/pets/{id}")
    public ResponseEntity<PetResponse> update(@PathVariable("id") int id, @RequestBody @Validated PetUpdateWeightRequest petUpdateWeightRequest) {
        Pet updatedPet = petService.update(id, petUpdateWeightRequest.getWeight());
        BigDecimal weightDifference = updatedPet.getWeight().subtract(updatedPet.getOldWeight());
        String responseMessage = String.format(updatedPet.getName() + "ちゃんの体重が更新されました。前回と比べて" + weightDifference + "kgの変化があります。");
        PetResponse body = new PetResponse(responseMessage);
        return ResponseEntity.ok(body);
    }
}

