package com.ternobo.wallet.user;

import com.ternobo.wallet.user.http.exceptions.UsernameDuplicatedException;
import com.ternobo.wallet.user.http.requests.UserDTO;
import com.ternobo.wallet.user.records.Role;
import com.ternobo.wallet.user.records.User;
import com.ternobo.wallet.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserCRUDTests {

    @Autowired
    private UserService service;

    @Test
    void createUserTest() {
        UserDTO userDTO = new UserDTO(
                "Test User",
                "test_" + UUID.randomUUID(),
                "142536",
                "142536"
        );
        User user = this.service.createUser(userDTO);
        assertEquals(user.getName(), userDTO.name());
        assertEquals(user.getRole(), Role.USER);
        assertEquals(user.getUsername(), userDTO.username());
    }

    @Test
    void createAndFindUserByUsernameTest() {
        UserDTO userDTO = new UserDTO(
                "Test User",
                "test_" + UUID.randomUUID(),
                "142536",
                "142536"
        );
        this.service.createUser(userDTO);
        assertInstanceOf(UserDetails.class, this.service.loadUserByUsername(userDTO.username()));
    }

    @Test
    void createDuplicatedUserTest() {
        UserDTO userDTO = new UserDTO(
                "Test User",
                "test_" + UUID.randomUUID(),
                "142536",
                "142536"
        );
        User user = this.service.createUser(userDTO);
        assertThrows(UsernameDuplicatedException.class, () -> this.service.createUser(userDTO));
    }
}
