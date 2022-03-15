package ru.expertek.keycloakadapter.controller;

import org.springframework.http.ResponseEntity;

public interface TokenWorker {

    public ResponseEntity<String> obtain(String username, String password);

    public ResponseEntity<String> refresh(String refreshToken);

    public ResponseEntity<String> rewoke(String token);

}
