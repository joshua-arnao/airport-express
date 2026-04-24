package com.autodidacta.identityservice.controller;

import com.autodidacta.identityservice.dto.RegisterRequest;
import com.autodidacta.identityservice.dto.RegisterResponse;
import com.autodidacta.identityservice.dto.UserResponse;
import com.autodidacta.identityservice.entity.Role;
import com.autodidacta.identityservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public UserResponse findUser(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/create")
    public RegisterResponse createUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.createUser(registerRequest, Role.CONDUCTOR);
    }
}
