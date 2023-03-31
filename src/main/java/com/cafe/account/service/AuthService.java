package com.cafe.account.service;

import com.cafe.account.dto.auth.JwtRequest;
import com.cafe.account.dto.auth.JwtResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuthService {

    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    };

    public JwtResponse refresh(String refreshToken){
        return null;
    };

    public List<String> getUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
