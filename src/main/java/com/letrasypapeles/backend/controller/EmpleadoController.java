package com.letrasypapeles.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoController {

    @Operation(
        summary = "Acceso exclusivo para empleados",
        description = "Devuelve un mensaje si el usuario autenticado tiene el rol EMPLEADO"
    )
    @ApiResponse(responseCode = "200", description = "Acceso autorizado como EMPLEADO")
    @PreAuthorize("hasRole('EMPLEADO')")
    @GetMapping("/empleado")
    public ResponseEntity<String> empleado() {
        return ResponseEntity.ok("Solo los empleados pueden ver este mensaje");
    }
}
