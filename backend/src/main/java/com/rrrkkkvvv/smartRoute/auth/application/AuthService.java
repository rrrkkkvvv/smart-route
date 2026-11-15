package com.rrrkkkvvv.smartRoute.auth.application;


import com.rrrkkkvvv.smartRoute.auth.api.dto.SignInRequest;
import com.rrrkkkvvv.smartRoute.auth.api.dto.SignUpRequest;
import com.rrrkkkvvv.smartRoute.auth.application.dto.AuthResult;
import com.rrrkkkvvv.smartRoute.auth.domain.InvalidCredentialsException;
import com.rrrkkkvvv.smartRoute.auth.domain.UserAlreadyExistsException;
import com.rrrkkkvvv.smartRoute.auth.security.JwtService;
import com.rrrkkkvvv.smartRoute.auth.security.JwtUserPrincipal;
import com.rrrkkkvvv.smartRoute.user.application.port.out.UserRepository;
import com.rrrkkkvvv.smartRoute.user.domain.exception.UserNotFoundException;
import com.rrrkkkvvv.smartRoute.user.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResult signUp(SignUpRequest signUpRequest) {

        if(userRepository.existsByEmail(signUpRequest.email())){
            throw new UserAlreadyExistsException();
        }
        String passwordHash =  passwordEncoder.encode(signUpRequest.password());
        User user = new User(UUID.randomUUID(), signUpRequest.email(), signUpRequest.name(), passwordHash);
        userRepository.save(user);
        String token = jwtService.buildToken(new JwtUserPrincipal(user.id(), user.email()));

        return new AuthResult(user, token);

    }
    public AuthResult signIn(SignInRequest signInRequest) {
        User user =userRepository.findByEmail(signInRequest.email()).orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(signInRequest.password(),user.passwordHash())){
            throw new InvalidCredentialsException();
        }
        String token = jwtService.buildToken(new JwtUserPrincipal(user.id(), user.email()));

        return new AuthResult(user, token);

    }
    public AuthResult refresh(JwtUserPrincipal currentUser) {
        User user =userRepository.findByEmail(currentUser.email()).orElseThrow(UserNotFoundException::new);
        String token = jwtService.buildToken(new JwtUserPrincipal(user.id(), user.email()));
        return new AuthResult(user, token);

    }
}
