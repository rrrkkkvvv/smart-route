package com.rrrkkkvvv.smartRoute.route.domain;

public class UnsupportedRouteProfileException extends RuntimeException {
    public UnsupportedRouteProfileException(String message) {
        super( "Unsupported route profile: " +message);
    }
}
