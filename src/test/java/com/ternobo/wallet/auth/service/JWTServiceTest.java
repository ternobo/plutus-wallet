package com.ternobo.wallet.auth.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class JWTServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    private DecodedJWT decodedJWT;
    private User user;
    private String jwt;

    @BeforeEach
    void setUp() {
        UserDTO userDTO = new UserDTO(
                "Test User",
                "test_" + UUID.randomUUID(),
                "142536",
                "142536"
        );
        user = this.userService.createUser(userDTO);
        jwt = this.jwtService.generateToken(user);
        this.decodedJWT = this.jwtService.validateJWT(jwt);
    }

    @Test
    void createJTWTokenTest() {
        assertEquals(decodedJWT.getIssuer(), "ternobo");
        assertEquals(decodedJWT.getSubject(), String.valueOf(user.getId()));
    }

    @Test
    void validateJWT() {
        assertEquals(decodedJWT.getIssuer(), "ternobo");
        assertEquals(decodedJWT.getSubject(), String.valueOf(user.getId()));

        this.jwtService.invokeToken(jwt);
        assertThrows(JWTVerificationException.class, () -> this.jwtService.validateJWT(jwt));
    }
}