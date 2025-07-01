package com.letrasypapeles.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

class JwtGeneratorTest {

    private JwtGenerator jwtGenerator;

    @BeforeEach
    void setUp() {
        jwtGenerator = new JwtGenerator();
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        Authentication auth = new UsernamePasswordAuthenticationToken("testuser", null);
        String token = jwtGenerator.generateToken(auth);

        assertNotNull(token);
        String username = jwtGenerator.getUsernameFromJwtToken(token);
        assertEquals("testuser", username);
    }

    @Test
    void testValidateJwtToken_Valid() {
        Authentication auth = new UsernamePasswordAuthenticationToken("validuser", null);
        String token = jwtGenerator.generateToken(auth);

        assertTrue(jwtGenerator.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_Invalid() {
        String invalidToken = "this.is.not.a.valid.token";

        Exception exception = assertThrows(
                Exception.class,
                () -> jwtGenerator.validateJwtToken(invalidToken)
        );

        assertTrue(exception.getMessage().contains("JWT"));
    }
}
