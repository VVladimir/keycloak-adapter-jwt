package ru.expertek.keycloakadapter.controller;

import org.springframework.http.ResponseEntity;

public interface TokenWorker {

    ResponseEntity<String> obtain(String username, String password);

    ResponseEntity<String> refresh(String refreshToken);

    ResponseEntity<String> revoke(String token);

    ResponseEntity<String> logout(String token);


}
