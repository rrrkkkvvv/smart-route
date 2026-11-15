package com.rrrkkkvvv.smartRoute.auth.unit;

import com.rrrkkkvvv.smartRoute.auth.api.dto.SignInRequest;
import com.rrrkkkvvv.smartRoute.auth.api.dto.SignUpRequest;
import com.rrrkkkvvv.smartRoute.auth.application.AuthService;
import com.rrrkkkvvv.smartRoute.auth.application.dto.AuthResult;
import com.rrrkkkvvv.smartRoute.auth.domain.InvalidCredentialsException;
import com.rrrkkkvvv.smartRoute.auth.security.JwtService;
import com.rrrkkkvvv.smartRoute.user.application.port.out.UserRepository;
import com.rrrkkkvvv.smartRoute.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, })
public class AuthServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    @BeforeEach
    public void init(){
        passwordEncoder = new BCryptPasswordEncoder();

        this.authService = new AuthService(
                userRepository,
                passwordEncoder,
                jwtService

        );
    }
    @Test
    void signUp_shouldEncodePasswordAndSaveUser() {

        SignUpRequest request = new SignUpRequest(
                "test@example.com",
                "test",
                "password123"
        );

        when(userRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(jwtService.buildToken(any()))
                .thenReturn("test-jwt");


        AuthResult result = authService.signUp(request);


        ArgumentCaptor<User> captor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());

        User savedUser = captor.getValue();

        assertThat(savedUser.passwordHash())
                .isNotEqualTo(request.password());

        assertThat(passwordEncoder.matches(
                request.password(),
                savedUser.passwordHash()
        )).isTrue();

        assertThat(result.token()).isEqualTo("test-jwt");
    }
    @Test
    void signIn_shouldThrow_whenPasswordIsInvalid() {
        User user = createTestUser(
                "test@example.com",
                "test",
                passwordEncoder.encode("password123")
        );
        when(userRepository.findByEmail(user.email()))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(() ->
                authService.signIn(new SignInRequest(
                        "test@example.com",
                        "wrong-password"
                ))
        ).isInstanceOf(InvalidCredentialsException.class);

        verify(jwtService, never()).buildToken(any());
    }
    @Test
    void signIn_shouldReturnToken_whenCredentialsAreValid() {
        User user = createTestUser(
                "test@example.com",
                "test",
                passwordEncoder.encode("password123")
        );

        when(userRepository.findByEmail(user.email()))
                .thenReturn(Optional.of(user));

        when(jwtService.buildToken(any()))
                .thenReturn("test-jwt");

        AuthResult result = authService.signIn(
                new SignInRequest("test@example.com", "password123")
        );

        assertThat(result.token()).isEqualTo("test-jwt");

        verify(jwtService).buildToken(any());
    }
    private User createTestUser(String email, String name, String password){
        return  new User(
                UUID.randomUUID(),
                email,
                name,
                password
        );
    }
    }
