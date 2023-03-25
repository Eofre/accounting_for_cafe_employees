package com.cafe.account.service;

import com.cafe.account.dto.auth.JwtRequest;
import com.cafe.account.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    };

    public JwtResponse refresh(String refreshToken){
        return null;
    };

}
