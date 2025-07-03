package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(
        summary = "Obtener todos los roles",
        description = "Devuelve una lista con todos los roles registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<RoleEntity>> obtenerTodos() {
        List<RoleEntity> roles = roleService.obtenerTodos();
        return ResponseEntity.ok(roles);
    }

    @Operation(
        summary = "Obtener rol por nombre",
        description = "Devuelve los datos de un rol específico según su nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{nombre}")
    public ResponseEntity<RoleEntity> obtenerPorNombre(
        @Parameter(description = "Nombre del rol a buscar", required = true)
        @PathVariable String nombre) {

        return roleService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear un nuevo rol",
        description = "Registra un nuevo rol en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Rol creado exitosamente")
    @PostMapping
    public ResponseEntity<RoleEntity> crearRole(
        @Parameter(description = "Datos del rol a registrar", required = true)
        @RequestBody RoleEntity role) {

        RoleEntity nuevoRole = roleService.guardar(role);
        return ResponseEntity.ok(nuevoRole);
    }

    @Operation(
        summary = "Eliminar un rol por nombre",
        description = "Elimina un rol del sistema según su nombre"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminarRole(
        @Parameter(description = "Nombre del rol a eliminar", required = true)
        @PathVariable String nombre) {

        return roleService.obtenerPorNombre(nombre)
                .map(r -> {
                    roleService.eliminar(nombre);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
