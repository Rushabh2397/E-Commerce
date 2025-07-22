package com.rushabh.ecommerce.dto.response;

public class AuthResponse {
    private UserResponse user;

    public AuthResponse(UserResponse user) {
        this.user = user;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
