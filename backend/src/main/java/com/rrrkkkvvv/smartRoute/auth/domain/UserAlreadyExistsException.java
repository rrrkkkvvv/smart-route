package com.rrrkkkvvv.smartRoute.auth.domain;

public class UserAlreadyExistsException extends RuntimeException{
    public  UserAlreadyExistsException(){
        super("User already exists");
    }
}
