package com.episen.episenmssecurity.models;

/**
 * Define the response of the authenticate method which is a JWT
 */
public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse (String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
