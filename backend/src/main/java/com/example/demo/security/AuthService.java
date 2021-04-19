package com.example.demo.security;

import com.example.demo.security.dto.SignupRequest;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void register(SignupRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .build();


        if (signUpRequest.getRole() == null) {
            user.setRole(roleRepository.findByName(ERole.REGULAR)
                    .orElseThrow(() -> new RuntimeException("Cannot find REGULAR role")));

        } else {
            user.setRole(roleRepository.findByName(ERole.valueOf(signUpRequest.getRole()))
            .orElseThrow(() -> new RuntimeException("Cannot find supplied role")));
        }
        userRepository.save(user);
    }
}
