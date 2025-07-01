package com.letrasypapeles.backend.security;

import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.entity.UserEntity;
import com.letrasypapeles.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailServiceTest {

    private UserRepository userRepository;
    private CustomUserDetailService userDetailService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userDetailService = new CustomUserDetailService(userRepository);
    }

    @Test
    void testLoadUserByUsername_UsuarioExistente() {
        UserEntity user = new UserEntity();
        user.setUsername("admin");
        user.setPassword("securepass");
        RoleEntity role = new RoleEntity();
        role.setName("GERENTE");
        user.setRoles(List.of(role));


        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailService.loadUserByUsername("admin");

        assertEquals("admin", userDetails.getUsername());
        assertEquals("securepass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GERENTE")));
    }

    @Test
    void testLoadUserByUsername_UsuarioNoExistente() {
        when(userRepository.findByUsername("inexistente")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailService.loadUserByUsername("inexistente");
        });
    }
}
