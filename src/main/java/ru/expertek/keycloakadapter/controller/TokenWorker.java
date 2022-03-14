package ru.expertek.keycloakadapter.controller;

import org.springframework.http.ResponseEntity;

public interface TokenWorker {
    public ResponseEntity<String> obtainNewJSONtoken(String username, String password);
}
