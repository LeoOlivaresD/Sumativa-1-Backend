package com.letrasypapeles.backend.security;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.entity.UserEntity;
import com.letrasypapeles.backend.repository.UserRepository;

class CustomUserDetailServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailService userDetailService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailService = new CustomUserDetailService(userRepository);
    }

    @Test
    void testLoadUserByUsername_UsuarioExiste() {
        UserEntity user = new UserEntity();
        user.setUsername("juan");
        user.setPassword("clave");
        user.setRoles(List.of(new RoleEntity("ADMIN")));

        when(userRepository.findByUsername("juan")).thenReturn(Optional.of(user));

        UserDetails result = userDetailService.loadUserByUsername("juan");

        assertEquals("juan", result.getUsername());
        assertEquals("clave", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testLoadUserByUsername_UsuarioNoExiste() {
        when(userRepository.findByUsername("noexiste")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailService.loadUserByUsername("noexiste"));
    }
}
