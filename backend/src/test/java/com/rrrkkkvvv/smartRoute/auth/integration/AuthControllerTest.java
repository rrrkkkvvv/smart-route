package com.rrrkkkvvv.smartRoute.auth.integration;

import com.rrrkkkvvv.smartRoute.auth.api.mapper.AuthApiMapper;
import com.rrrkkkvvv.smartRoute.auth.application.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private AuthApiMapper authApiMapper;

    @Test
    void signUp_shouldReturnBadRequest_whenEmailIsInvalid()
            throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "invalid-email",
                        "name": "test",
                        "password": "password123"
                    }
                    """))
                .andExpect(status().is(400));
    }
    @Test
    void signUp_shouldReturnOk_whenRequestIsValid() throws Exception {

        mockMvc.perform(post("/api/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())

                        .content("""
                {
                    "email": "tests@example.com",
                    "name": "test",
                    "password": "password123"
                }
                """))
                .andExpect(status().is(201));
    }
}
