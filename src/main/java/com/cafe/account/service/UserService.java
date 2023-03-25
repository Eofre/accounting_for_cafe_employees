package com.cafe.account.service;

import com.cafe.account.dto.user.UserDto;
import com.cafe.account.models.User;
import com.cafe.account.models.Role;
import com.cafe.account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create (UserDto userDto) {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ADMIN);

        return userRepository.save(user);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User authenticate(UserDto userDto) throws AuthenticationException {
        Optional<User> userOptional = getByUsername(userDto.getUsername());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return user;
    }

}
