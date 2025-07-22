package com.rushabh.ecommerce.service.interfaces;

import com.rushabh.ecommerce.dto.request.LoginRequest;
import com.rushabh.ecommerce.dto.request.RegisterRequest;
import com.rushabh.ecommerce.dto.response.AuthResponse;
import com.rushabh.ecommerce.dto.response.LoginResponse;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    LoginResponse login(@Valid LoginRequest req);
}
