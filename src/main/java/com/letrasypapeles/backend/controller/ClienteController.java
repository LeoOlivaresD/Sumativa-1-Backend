package com.letrasypapeles.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("cliente")
    public ResponseEntity<String> cliente() {
        return ResponseEntity.ok("Eres el cliente");
    }
}