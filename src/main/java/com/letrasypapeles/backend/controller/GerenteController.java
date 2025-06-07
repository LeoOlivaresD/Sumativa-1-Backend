package com.letrasypapeles.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GerenteController {

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("gerente")
    public ResponseEntity<String> gerente() {
        return ResponseEntity.ok("Acceso solo a Gerente");
    }
}
