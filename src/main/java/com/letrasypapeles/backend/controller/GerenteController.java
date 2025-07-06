package com.letrasypapeles.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GerenteController {

    @Operation(
        summary = "Acceso exclusivo para gerentes",
        description = "Devuelve un mensaje si el usuario autenticado tiene el rol GERENTE"
    )
    @ApiResponse(responseCode = "200", description = "Acceso autorizado como GERENTE")
    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/gerente")
    public ResponseEntity<String> gerente() {
        return ResponseEntity.ok("Acceso solo a Gerente");
    }
}
