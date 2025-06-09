package com.letrasypapeles.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpleadoController {

    @PreAuthorize("hasRole('EMPLEADO')")
    @GetMapping("empleado")
    public ResponseEntity<String> empleado() {
        return ResponseEntity.ok("Solo los empleados pueden ver este mensaje");
    }
}