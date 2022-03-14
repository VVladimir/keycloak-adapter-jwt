package ru.expertek.keycloakadapter.controller;

import org.springframework.http.ResponseEntity;

public interface TokenWorker {

    public ResponseEntity<String> obtainJWT(String username, String password);

    public ResponseEntity<String> refreshJWT(String refreshToken);
}
