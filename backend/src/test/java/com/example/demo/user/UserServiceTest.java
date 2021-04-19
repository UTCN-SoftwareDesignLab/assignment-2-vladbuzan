package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.security.CurrentRoleService;
import com.example.demo.user.dto.UpdateUserRequest;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CurrentRoleService roleService;
    
    private final Role role = Role.builder()
            .id(1L)
            .name(ERole.REGULAR)
            .build();
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper, roleRepository, roleService);
        
        when(roleService.isAdmin()).thenReturn(true);
        when(roleRepository.findByName(ERole.REGULAR)).thenReturn(Optional.of(role));
    }

    @Test
    void allRegularUsers() {

        Optional<Object> optional = Optional.of((Object) ERole.REGULAR);
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAllByRole(role)).thenReturn(users);
        List<UserListDto> userListDtos = userService.allRegularUsersForList();
        Assertions.assertEquals(users.size(), userListDtos.size());
    }

    @Test
    void updateUser() {
        User user = TestCreationFactory.newUser();
        var userDTO = UserListDto.builder()
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userListDtoFromUser(user)).thenReturn(userDTO);
        UpdateUserRequest request = UpdateUserRequest.builder()
                .username("")
                .email("")
                .role("")
                .build();
        Assertions.assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    void deleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        Assertions.assertDoesNotThrow(() -> userService.deleteUser(1L));
    }
}
