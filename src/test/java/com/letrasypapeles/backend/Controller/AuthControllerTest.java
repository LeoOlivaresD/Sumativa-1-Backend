package com.letrasypapeles.backend.controller;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.letrasypapeles.backend.controller.AuthController;
import com.letrasypapeles.backend.dto.AuthResponseDTO;
import com.letrasypapeles.backend.dto.LoginDTO;
import com.letrasypapeles.backend.dto.RegisterDTO;
import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.entity.UserEntity;
import com.letrasypapeles.backend.repository.RoleRepository;
import com.letrasypapeles.backend.repository.UserRepository;
import com.letrasypapeles.backend.security.JwtGenerator;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtGenerator jwtGenerator;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("test");
        loginDTO.setPassword("123");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtGenerator.generateToken(authentication)).thenReturn("fake-jwt");

        ResponseEntity<AuthResponseDTO> response = authController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("fake-jwt", response.getBody().getAccessToken());
    }

    @Test
    void testRegistroUsuarioYaExiste() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("existingUser");
        dto.setPassword("123");

        when(userRepository.existsByUsername("existingUser")).thenReturn(true);

        ResponseEntity<String> response = authController.registro(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El usuario ya existe", response.getBody());
    }

    @Test
    void testRegistroUsuarioNuevoConRolExistente() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("nuevo");
        dto.setPassword("123");

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName("EMPLEADO");

        when(userRepository.existsByUsername("nuevo")).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(roleRepository.findByName("EMPLEADO")).thenReturn(Optional.of(role));

        ResponseEntity<String> response = authController.registro(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario registrado de forma exitosa.", response.getBody());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testRegistroUsuarioNuevoConRolInexistente() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("nuevo");
        dto.setPassword("123");

        RoleEntity nuevoRol = new RoleEntity();
        nuevoRol.setId(2L);
        nuevoRol.setName("EMPLEADO");

        when(userRepository.existsByUsername("nuevo")).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(roleRepository.findByName("EMPLEADO")).thenReturn(Optional.empty());
        when(roleRepository.save(any())).thenReturn(nuevoRol);

        ResponseEntity<String> response = authController.registro(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario registrado de forma exitosa.", response.getBody());
    }
}
