package com.rrrkkkvvv.smartRoute.user.domain.exception;


public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(){
        super("User not found");
    }
}
