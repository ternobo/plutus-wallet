package com.ternobo.wallet.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.Role;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class JTWServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private JTWService jwtService;

    @Test
    void createJTWTokenTest() {
        UserDTO userDTO = new UserDTO(
                "Test User",
                "test_" + UUID.randomUUID(),
                "142536",
                "142536"
        );
        User user = this.userService.createUser(userDTO);
        String jwt = this.jwtService.generateToken(user);
        DecodedJWT decodedJWT = this.jwtService.validateJWT(jwt);
        assertEquals(decodedJWT.getIssuer(),"ternobo");
        assertEquals(decodedJWT.getSubject(), String.valueOf(user.getId()));
        System.out.println(jwt);
    }

}