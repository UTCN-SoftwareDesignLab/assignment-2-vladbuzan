package com.example.demo.user;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.security.CurrentRoleService;
import com.example.demo.user.dto.UpdateUserRequest;
import com.example.demo.user.dto.UserListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.newUserListDto;
import static com.example.demo.UrlMapping.USER;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDto> users = TestCreationFactory.listOf(UserListDto.class);
        when(userService.allRegularUsersForList()).thenReturn(users);
        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));
    }

    @Test
    void updateUser() throws Exception {
        var request = UpdateUserRequest.builder()
                .email("newMail@gmail.com")
                .role("REGULAR")
                .build();
        var user = newUserListDto();
        when(userService.updateUser(1L, request)).thenReturn(user);
        ResultActions result = performPutWithRequestBodyAndPathVariable(USER + "/{id}", request, String.valueOf(1L));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void removeUser() throws Exception {
        ResultActions result = performDeleteWIthPathVariable(USER + "/{id}", 1L);
        result.andExpect(status().isOk());
    }
}
