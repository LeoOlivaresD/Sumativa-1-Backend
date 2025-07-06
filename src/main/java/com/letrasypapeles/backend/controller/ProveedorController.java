package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Proveedor;
import com.letrasypapeles.backend.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Operation(
        summary = "Obtener todos los proveedores",
        description = "Devuelve una lista con todos los proveedores registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodos() {
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        return ResponseEntity.ok(proveedores);
    }

    @Operation(
        summary = "Obtener proveedor por ID",
        description = "Devuelve los datos de un proveedor específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(
        @Parameter(description = "ID del proveedor a buscar", required = true)
        @PathVariable Long id) {

        return proveedorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear un nuevo proveedor",
        description = "Registra un nuevo proveedor en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Proveedor creado exitosamente")
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(
        @Parameter(description = "Datos del proveedor a registrar", required = true)
        @RequestBody Proveedor proveedor) {

        Proveedor nuevoProveedor = proveedorService.guardar(proveedor);
        return ResponseEntity.ok(nuevoProveedor);
    }

    @Operation(
        summary = "Actualizar un proveedor existente",
        description = "Modifica los datos de un proveedor según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(
        @Parameter(description = "ID del proveedor a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados del proveedor", required = true)
        @RequestBody Proveedor proveedor) {

        return proveedorService.obtenerPorId(id)
                .map(p -> {
                    proveedor.setId(id);
                    Proveedor proveedorActualizado = proveedorService.guardar(proveedor);
                    return ResponseEntity.ok(proveedorActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar un proveedor",
        description = "Elimina un proveedor del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(
        @Parameter(description = "ID del proveedor a eliminar", required = true)
        @PathVariable Long id) {

        return proveedorService.obtenerPorId(id)
                .map(p -> {
                    proveedorService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
