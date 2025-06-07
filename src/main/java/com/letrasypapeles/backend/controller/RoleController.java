package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleEntity>> obtenerTodos() {
        List<RoleEntity> roles = roleService.obtenerTodos();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<RoleEntity> obtenerPorNombre(@PathVariable String nombre) {
        return roleService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoleEntity> crearRole(@RequestBody RoleEntity role) {
        RoleEntity nuevoRole = roleService.guardar(role);
        return ResponseEntity.ok(nuevoRole);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminarRole(@PathVariable String nombre) {
        return roleService.obtenerPorNombre(nombre)
                .map(r -> {
                    roleService.eliminar(nombre);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
