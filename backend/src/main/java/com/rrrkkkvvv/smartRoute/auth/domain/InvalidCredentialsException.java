package com.rrrkkkvvv.smartRoute.auth.domain;


public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}