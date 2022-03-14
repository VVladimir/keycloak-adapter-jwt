package ru.expertek.keycloakadapter.controller;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginForm {
    private String username;
    private String password;
    @JsonProperty("refresh_token")
    private String refreshToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
