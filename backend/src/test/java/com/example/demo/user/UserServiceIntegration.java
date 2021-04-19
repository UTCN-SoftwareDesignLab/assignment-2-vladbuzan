package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.security.AuthService;
import com.example.demo.security.CurrentRoleService;
import com.example.demo.user.dto.UpdateUserRequest;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.Target;
import java.util.List;

@SpringBootTest
public class UserServiceIntegration {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CurrentRoleService currentRoleService;

    @BeforeEach
    void setUp() {
        Role role = Role.builder()
                .name(ERole.REGULAR)
                .id(1L)
                .build();
        if(!roleRepository.existsById(1L)) {
            roleRepository.save(role);
        }
        userRepository.deleteAll();

    }

    @Test
    void findAllRegular() {
        List<User> users = TestCreationFactory.listOf(User.class);
        userRepository.saveAll(users);
        var usersDto = userService.allRegularUsersForList();
        Assertions.assertEquals(users.size(), usersDto.size());
    }

    @Test
    void updateUser() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);
        var request = UpdateUserRequest.builder()
                .email("Vlad@gmail.com")
                .username("")
                .role("")
                .build();
        var userID = userRepository.findByUsername(user.getUsername()).get().getId();
        Assertions.assertEquals("Vlad@gmail.com",
                userService.updateUser(userID, request).getEmail());
    }

    @Test
    void deleteUser() {
        User user = TestCreationFactory.newUser();
        userRepository.save(user);
        var userID = userRepository.findByUsername(user.getUsername())
                .get().getId();
        userService.deleteUser(userID);
        Assertions.assertFalse(userRepository.existsByEmail(user.getEmail()));
    }
}
