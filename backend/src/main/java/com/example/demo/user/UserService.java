package com.example.demo.user;

import com.example.demo.security.CurrentRoleService;
import com.example.demo.user.dto.UpdateUserRequest;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final CurrentRoleService currentRoleService;

    public List<UserListDto> allRegularUsersForList() {
        /*if (!currentRoleService.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }*/
        Role role = roleRepository.findByName(ERole.REGULAR)
                .orElseThrow(() -> new RuntimeException("Couldn't find role"));
        return userRepository.findAllByRole(role)
                .stream().map(userMapper::userListDtoFromUser)
                .collect(Collectors.toList());
    }

    public UserListDto updateUser(Long id, UpdateUserRequest request) {
        /*if (!currentRoleService.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }*/
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find user"));
        if (!request.getUsername().equals("")) {
            user.setUsername(request.getUsername());
        }
        if (!request.getEmail().equals("")) {
            user.setEmail(request.getEmail());
        }
        if (!request.getRole().equals("")) {
            Role role = roleRepository.findByName(ERole.valueOf(request.getRole()))
                    .orElseThrow(() -> new RuntimeException("Couldn't find role"));
            user.setRole(role);
        }
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        /*if (!currentRoleService.isAdmin()) {
            throw new RuntimeException("Unauthorized");
        }*/
        userRepository.deleteById(id);
    }
}
