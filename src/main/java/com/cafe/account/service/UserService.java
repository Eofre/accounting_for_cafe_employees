package com.cafe.account.service;

import com.cafe.account.models.User;
import com.cafe.account.models.Role;
import com.cafe.account.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registration (User user) {
      return userRepository.save(user);
    }

}
