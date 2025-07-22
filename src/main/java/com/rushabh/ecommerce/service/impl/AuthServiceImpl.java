package com.rushabh.ecommerce.service.impl;

import com.rushabh.ecommerce.dto.request.LoginRequest;
import com.rushabh.ecommerce.dto.request.RegisterRequest;
import com.rushabh.ecommerce.dto.response.AuthResponse;
import com.rushabh.ecommerce.dto.response.LoginResponse;
import com.rushabh.ecommerce.dto.response.UserResponse;
import com.rushabh.ecommerce.entity.User;
import com.rushabh.ecommerce.repository.UserRepository;
import com.rushabh.ecommerce.security.JwtService;
import com.rushabh.ecommerce.security.UserPrincipal;
import com.rushabh.ecommerce.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user =
                new User(
                        request.getEmail(),
                        request.getPassword(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPhone(),
                        true);
        user.setPassword(encoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        UserResponse userRes =
                new UserResponse(
                        savedUser.getId(),
                        savedUser.getFirstName(),
                        savedUser.getLastName(),
                        savedUser.getPhone());
        return new AuthResponse(userRes);
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        User user = userPrincipal.getUser();
        String token = jwtService.generateAccessToken(user.getEmail());

        UserResponse userRes =
                new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhone());

        return new LoginResponse(userRes, token);


    }
}
