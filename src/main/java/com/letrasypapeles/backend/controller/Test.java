package com.letrasypapeles.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.letrasypapeles.backend.security.CustomUserDetailService;

@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @GetMapping("/userDetails")
    public ResponseEntity<String> getUserDetails(@RequestParam String username) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        return ResponseEntity.ok("Usuario: " + userDetails.getUsername() + " - Roles: " + userDetails.getAuthorities());
    }
}
