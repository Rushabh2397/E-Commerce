package com.rushabh.ecommerce.service.interfaces;

import com.rushabh.ecommerce.dto.request.LoginRequest;
import com.rushabh.ecommerce.dto.request.RegisterRequest;
import com.rushabh.ecommerce.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    void login(LoginRequest request);
}
