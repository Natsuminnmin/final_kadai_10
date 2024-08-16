package com.example.petmanagment;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message){
        super(message);
    }
}
