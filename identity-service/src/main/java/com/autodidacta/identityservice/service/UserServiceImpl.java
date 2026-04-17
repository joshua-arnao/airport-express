package com.autodidacta.identityservice.service;

import com.autodidacta.identityservice.dto.*;
import com.autodidacta.identityservice.entity.Role;
import com.autodidacta.identityservice.entity.User;
import com.autodidacta.identityservice.mapper.UserMapper;
import com.autodidacta.identityservice.repository.UserRepository;
import com.autodidacta.identityservice.shared.exceptions.EmailAlreadyExistsException;
import com.autodidacta.identityservice.shared.exceptions.InvalidCredentialsException;
import com.autodidacta.identityservice.shared.exceptions.PhoneNumberAlreadyExistsException;
import com.autodidacta.identityservice.shared.exceptions.UserNotFoundException;
import com.autodidacta.identityservice.shared.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        userRepository.findByEmail(registerRequest.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException("Email is in use");
                });

        userRepository.findByPhoneNumber(registerRequest.phoneNumber())
                .ifPresent(user -> {
                    throw new PhoneNumberAlreadyExistsException("Phone is in use");
                });

        String hashedPassword = passwordEncoder.encode(registerRequest.password());

        User user = User.builder()
                .firstName(registerRequest.firstname())
                .lastName(registerRequest.lastname())
                .documentType(registerRequest.documentType())
                .documentNumber(registerRequest.documentNumber())
                .email(registerRequest.email())
                .phoneNumber(registerRequest.phoneNumber())
                .password(hashedPassword)
                .role(Role.PASSENGER)
                .build();

        User savedUser = userRepository.save(user);

        // return new RegisterResponse(
        // savedUser.getUserId(),
        // savedUser.getFirstName(),
        // savedUser.getLastName(),
        // savedUser.getEmail(),
        // savedUser.getPhoneNumber());

        return userMapper.registerToResponse(savedUser);
    }

    @Override
    public RegisterResponse createUser(RegisterRequest registerRequest, Role role) {
        userRepository.findByEmail(registerRequest.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException("Email is in use");
                });

        String hashedPassword = passwordEncoder.encode(registerRequest.password());

        User user = User.builder()
                .firstName(registerRequest.firstname())
                .lastName(registerRequest.lastname())
                .documentType(registerRequest.documentType())
                .documentNumber(registerRequest.documentNumber())
                .email(registerRequest.email())
                .phoneNumber(registerRequest.phoneNumber())
                .password(hashedPassword)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        // return new RegisterResponse(
        // savedUser.getUserId(),
        // savedUser.getFirstName(),
        // savedUser.getLastName(),
        // savedUser.getEmail(),
        // savedUser.getPhoneNumber());

        return userMapper.registerToResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user;

        if (loginRequest.identifier().contains("@")) {
            user = userRepository.findByEmail(loginRequest.identifier())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } else {
            user = userRepository.findByPhoneNumber(loginRequest.identifier())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole());
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        // return new UserResponse(
        // user.getUserId(),
        // user.getFirstName(),
        // user.getLastName(),
        // user.getRole(),
        // user.getEmail(),
        // user.getPhoneNumber());

        return userMapper.userToResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not fund"));
    }
}
