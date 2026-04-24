package com.autodidacta.identityservice.controller;

import com.autodidacta.identityservice.dto.LoginRequest;
import com.autodidacta.identityservice.dto.LoginResponse;
import com.autodidacta.identityservice.dto.RegisterRequest;
import com.autodidacta.identityservice.dto.RegisterResponse;
import com.autodidacta.identityservice.entity.Role;
import com.autodidacta.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public RegisterResponse registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @PostMapping("login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}
