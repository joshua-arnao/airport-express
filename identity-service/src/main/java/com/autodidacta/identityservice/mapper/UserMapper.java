package com.autodidacta.identityservice.mapper;

import org.mapstruct.Mapper;

import com.autodidacta.identityservice.dto.RegisterResponse;
import com.autodidacta.identityservice.dto.UserResponse;
import com.autodidacta.identityservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RegisterResponse registerToResponse(User user);

    UserResponse userToResponse(User user);
}
