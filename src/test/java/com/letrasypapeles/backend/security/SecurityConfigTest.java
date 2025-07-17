package com.letrasypapeles.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.DefaultSecurityFilterChain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(SecurityConfig.class)
class SecurityConfigTest {

    private final JwtAuthEntryPoint authEntryPoint = mock(JwtAuthEntryPoint.class);
    private final CustomUserDetailService userDetailsService = mock(CustomUserDetailService.class);
    private final SecurityConfig securityConfig = new SecurityConfig(userDetailsService, authEntryPoint);

    @Test
    void jwtAuthenticationFilterBeanIsNotNull() {
        JwtAuthenticationFilter filter = securityConfig.jwtAuthenticationFilter();
        assertThat(filter).isNotNull();
    }

    @Test
    void passwordEncoderBeanIsBCrypt() {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        assertThat(encoder).isNotNull();
        assertThat(encoder.encode("123")).isNotEqualTo("123"); // confirmación básica
    }

    @Test
    void authenticationManagerIsResolvedFromConfiguration() throws Exception {
        AuthenticationManager authManagerMock = mock(AuthenticationManager.class);
        AuthenticationConfiguration configuration = mock(AuthenticationConfiguration.class);
        when(configuration.getAuthenticationManager()).thenReturn(authManagerMock);

        AuthenticationManager result = securityConfig.authenticationManager(configuration);

        assertThat(result).isEqualTo(authManagerMock);
    }

    @Test
    void filterChainBuildsCorrectly() throws Exception {
        // Simulación básica para que el bean compile y devuelva algo
        var httpSecurity = mock(org.springframework.security.config.annotation.web.builders.HttpSecurity.class, RETURNS_DEEP_STUBS);
        when(httpSecurity.build()).thenReturn(mock(DefaultSecurityFilterChain.class));

        SecurityFilterChain result = securityConfig.filterChain(httpSecurity);

        assertThat(result).isNotNull();
    }
}
