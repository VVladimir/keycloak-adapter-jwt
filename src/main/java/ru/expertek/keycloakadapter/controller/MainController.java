package ru.expertek.keycloakadapter.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
    public class MainController {

    @GetMapping("_ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/")
    @Hidden
    public ResponseEntity<?> root() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",  "/swagger-ui.html");
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }
}