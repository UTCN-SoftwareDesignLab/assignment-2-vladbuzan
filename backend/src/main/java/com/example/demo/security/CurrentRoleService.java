package com.example.demo.security;

import com.example.demo.user.model.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentRoleService {

    public ERole getLoggedInUserRole() {
        var roles = SecurityContextHolder
                .getContext().getAuthentication().getAuthorities()
                .stream().map(
                        e -> ERole.valueOf(e.getAuthority())
                ).collect(Collectors.toList());
        return roles.get(0);
    }

    public boolean isAdmin() {
        return getLoggedInUserRole().equals(ERole.ADMIN);
    }

    public boolean isRegular() {
        return getLoggedInUserRole().equals(ERole.REGULAR);
    }
}
