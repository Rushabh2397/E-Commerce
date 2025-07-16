package com.rushabh.ecommerce.service.impl;

import com.rushabh.ecommerce.dto.request.RegisterRequest;
import com.rushabh.ecommerce.dto.response.AuthResponse;
import com.rushabh.ecommerce.entity.User;
import com.rushabh.ecommerce.repository.UserRepository;
import com.rushabh.ecommerce.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhone(),
                true
        );
        User savedUser =  userRepository.save(user);
        return  new AuthResponse("");
    }
}
