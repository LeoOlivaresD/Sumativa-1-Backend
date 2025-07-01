package com.letrasypapeles.backend.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthEntryPointTest {

    @Test
    void testCommenceSendsUnauthorizedError() throws IOException, ServletException {
        JwtAuthEntryPoint entryPoint = new JwtAuthEntryPoint();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);

        when(exception.getMessage()).thenReturn("Unauthorized");

        entryPoint.commence(request, response, exception);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
