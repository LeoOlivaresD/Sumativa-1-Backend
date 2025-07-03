package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.dto.AuthResponseDTO;
import com.letrasypapeles.backend.dto.LoginDTO;
import com.letrasypapeles.backend.dto.RegisterDTO;
import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.entity.UserEntity;
import com.letrasypapeles.backend.repository.RoleRepository;
import com.letrasypapeles.backend.repository.UserRepository;
import com.letrasypapeles.backend.security.JwtGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica al usuario y devuelve un token JWT si las credenciales son válidas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @Parameter(description = "Credenciales de inicio de sesión", required = true)
        @RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Registra un nuevo usuario con el rol EMPLEADO y devuelve un mensaje de confirmación"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "El usuario ya existe")
    })
    @PostMapping("/registro")
    public ResponseEntity<String> registro(
        @Parameter(description = "Datos del nuevo usuario", required = true)
        @RequestBody RegisterDTO registerDTO) {

        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        RoleEntity role = roleRepository.findByName("EMPLEADO").orElseGet(() -> {
            RoleEntity newRole = new RoleEntity();
            newRole.setName("EMPLEADO");
            return roleRepository.save(newRole);
        });

        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        return new ResponseEntity<>("Usuario registrado de forma exitosa.", HttpStatus.CREATED);
    }
}
