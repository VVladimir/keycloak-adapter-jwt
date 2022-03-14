package ru.expertek.keycloakadapter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenWorkerImpl implements TokenWorker {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Override
    public ResponseEntity<String> obtainJWT(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        map.add("client_id", clientId);

        return getToken(map);

    }
    @Override
    public ResponseEntity<String> refreshJWT(String refreshToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", clientId);
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);
        return getToken(map);
    }

    private ResponseEntity<String> getToken(MultiValueMap<String, String> map) {
        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, null);
            ResponseEntity<String> response = restTemplate.postForEntity(accessTokenUri, request, String.class);
            return new ResponseEntity<String>(response.getBody(), null, response.getStatusCode());
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<String>(e.getMessage(), e.getStatusCode());
        }
    }




}