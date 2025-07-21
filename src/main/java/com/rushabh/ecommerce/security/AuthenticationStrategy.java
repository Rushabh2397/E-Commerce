package com.rushabh.ecommerce.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationStrategy {

        Authentication authenticate(String token);
        boolean supports(String authType);
        String getStrategyName();
        String generateAccessToken(UserDetails user);
}
