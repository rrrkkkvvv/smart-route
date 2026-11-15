package com.rrrkkkvvv.smartRoute.auth.api.mapper;

import com.rrrkkkvvv.smartRoute.auth.api.dto.AuthResponse;
import com.rrrkkkvvv.smartRoute.auth.api.dto.AuthenticatedUser;
import com.rrrkkkvvv.smartRoute.auth.application.dto.AuthResult;
import com.rrrkkkvvv.smartRoute.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthApiMapper {
    public AuthResponse toResponse(AuthResult result) {
        User user = result.user();

        AuthenticatedUser authUserResponse =
                new AuthenticatedUser(
                        user.id(),
                        user.email(),
                        user.name()
                );

        return new AuthResponse(
                authUserResponse, result.token()
        );
    }

}
