package com.rrrkkkvvv.smartRoute.auth.api;

import com.rrrkkkvvv.smartRoute.auth.api.dto.AuthResponse;
import com.rrrkkkvvv.smartRoute.auth.api.dto.SignInRequest;
import com.rrrkkkvvv.smartRoute.auth.api.dto.SignUpRequest;
import com.rrrkkkvvv.smartRoute.auth.api.mapper.AuthApiMapper;
import com.rrrkkkvvv.smartRoute.auth.application.AuthService;
import com.rrrkkkvvv.smartRoute.auth.security.JwtUserPrincipal;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthApiMapper mapper;
    public AuthController(AuthService authService, AuthApiMapper mapper) {
        this.authService = authService;
        this.mapper = mapper;
    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        log.info("User with email: '"+signUpRequest.name()+"' was signed up");
        return ResponseEntity.status(201).body(mapper.toResponse(authService.signUp(signUpRequest))) ;
    }
    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody @Valid SignInRequest signInRequest){
        log.info("Sign in by: '"+signInRequest.email());
        return ResponseEntity.status(200).body(mapper.toResponse(authService.signIn(signInRequest))) ;
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@AuthenticationPrincipal JwtUserPrincipal currentUser
    )  {

        return ResponseEntity.status(200).body(mapper.toResponse(authService.refresh(currentUser)));
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal JwtUserPrincipal currentUser
    )  {
        log.info("logout of "+currentUser.id());
        return ResponseEntity.noContent().build();
    }
}
