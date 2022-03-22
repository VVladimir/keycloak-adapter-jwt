package ru.expertek.keycloakadapter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AccessController {
    private static final Logger logger = LoggerFactory.getLogger(AccessController.class);
    private final TokenWorker tokenWorker;

    public AccessController(TokenWorker tokenWorker) {
        this.tokenWorker = tokenWorker;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody LoginForm loginForm) {
        logger.info("login: {}, pass: {}", loginForm.getUsername(), loginForm.getPassword());
        return tokenWorker.obtain(loginForm.getUsername(), loginForm.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LoginForm loginForm) {
        logger.info("logout: {}", "");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Jwt credentials = (Jwt) auth.getCredentials();
        String refreshToken = ""; // todo получить из заголовка
        return tokenWorker.logout(loginForm.getRefreshToken());
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<String> refreshToken(@RequestBody LoginForm loginForm) {
        logger.info("refresh token: {}", loginForm.getRefreshToken());
        return tokenWorker.refresh(loginForm.getRefreshToken());
    }

    @GetMapping("/revoke_token")
    public ResponseEntity<String> revokeToken() {
        String token = getTokenFromCurrentContext();
        logger.info("revoke token: {}", token);
        return tokenWorker.revoke(token);
    }

    public String getTokenFromCurrentContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Jwt credentials = (Jwt) auth.getCredentials();
        return credentials.getTokenValue();
    }

    @GetMapping("/anonymous")
    public String getAnonymousInfo() {
        return "Anonymous";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String getUserInfo() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "user info";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminInfo() {
        return "admin info";
    }

    @GetMapping("/service")
    @PreAuthorize("hasRole('SERVICE')")
    public String getServiceInfo() {
        return "service info";
    }

    @GetMapping("/name")
    public Object getName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
