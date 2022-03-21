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
    @Value("${spring.security.oauth2.client.client_secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.access-token-uri}")
    private String accessTokenUri;
    @Value("${spring.security.oauth2.client.revoke-token-uri}")
    private String revokeTokenUri;
    @Value("${spring.security.oauth2.client.logout-uri}")
    private String logout;


    @Override
    public ResponseEntity<String> obtain(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        return getToken(map);

    }
    @Override
    public ResponseEntity<String> refresh(String refreshToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        return getToken(map);
    }

    @Override
    public ResponseEntity<String> revoke(String token) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", token);
        map.add("token_type_hint", "access_token");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        return revokeToken(map);
    }

    @Override
    public ResponseEntity<String> logout(String token) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("refresh_token", token);
        map.add("token_type_hint", "refresh_token");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);

        return logoutToken(map);
    }

    private ResponseEntity<String> logoutToken(MultiValueMap<String, String> map) {
        return execute(logout, map);
    }

    private ResponseEntity<String> getToken(MultiValueMap<String, String> map) {
        return execute(accessTokenUri, map);
    }

    private ResponseEntity<String> revokeToken(MultiValueMap<String, String> map) {
        return execute(revokeTokenUri, map);
    }

    private ResponseEntity<String> execute(String uri, MultiValueMap<String, String> map) {
        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, null);
            ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
            return new ResponseEntity<>(response.getBody(), null, response.getStatusCode());
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }


}