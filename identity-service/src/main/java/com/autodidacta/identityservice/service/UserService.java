package com.autodidacta.identityservice.service;

import com.autodidacta.identityservice.dto.*;
import com.autodidacta.identityservice.entity.Role;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);
    RegisterResponse createUser(RegisterRequest registerRequest, Role role);
    LoginResponse login(LoginRequest loginRequest);
    UserResponse findByEmail(String email);
}
