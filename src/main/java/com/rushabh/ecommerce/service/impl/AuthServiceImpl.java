package com.rushabh.ecommerce.service.impl;

import com.rushabh.ecommerce.dto.request.LoginRequest;
import com.rushabh.ecommerce.dto.request.RegisterRequest;
import com.rushabh.ecommerce.dto.response.AuthResponse;
import com.rushabh.ecommerce.entity.User;
import com.rushabh.ecommerce.repository.UserRepository;
import com.rushabh.ecommerce.security.UserPrincipal;
import com.rushabh.ecommerce.security.jwt.JwtService;
import com.rushabh.ecommerce.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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

        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        UserDetails userDetails = new UserPrincipal(savedUser);
        String token = jwtService.generateAccessToken(userDetails);

        return new AuthResponse(token);
    }

    public void login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        if (auth.isAuthenticated()) {
            String token = jwtService.generateAccessToken(auth.getPrincipal().toString());
            System.out.println("token" + token);
        }

    }
}
