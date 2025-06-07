package com.letrasypapeles.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpleadoController {

    @PreAuthorize("hasRole('EMPLEADO')")
    @GetMapping("empleado")
    public ResponseEntity<String> empleado() {
        return ResponseEntity.ok("Solo los empleados pueden ver este mensaje");
    }
}