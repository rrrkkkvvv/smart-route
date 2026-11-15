package com.rrrkkkvvv.smartRoute.location.domain;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException() {
        super("Location not found");
    }
}
