package com.rushabh.ecommerce.security;

import com.rushabh.ecommerce.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationStrategy implements AuthenticationStrategy {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(String token) {

        String username = jwtService.extractUsername(token);
        if (username == null) {
            return null;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(token, userDetails)) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
        }

        return null;
    }

    @Override
    public boolean supports(String authType) {
        return "JWT".equalsIgnoreCase(authType);
    }

    @Override
    public String getStrategyName() {
        return "JWT";
    }

    @Override
    public String generateAccessToken(UserDetails user) {
        String token = jwtService.generateAccessToken(user);
        return  token;
    }
}
